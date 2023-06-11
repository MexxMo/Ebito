package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.dto.ResponseWrapperAdsDto;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.AdsImage;
import ru.digitalmagicians.ebito.exception.AdsValidationException;
import ru.digitalmagicians.ebito.mapper.AdsMapper;
import ru.digitalmagicians.ebito.repository.AdsRepository;
import ru.digitalmagicians.ebito.security.AccessChecker;
import ru.digitalmagicians.ebito.service.AdsImageService;
import ru.digitalmagicians.ebito.service.AdsService;
import ru.digitalmagicians.ebito.service.UserService;


import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;
    private final AdsMapper adsMapper;
    private final AdsImageService adsImageService;
    private final AccessChecker accessChecker;

    @Override
    public AdsDto createAds(MultipartFile image, CreateAdsDto properties, Authentication authentication) {
        if (validation(properties)) {
            log.error("empty fields CreateAdsDto createAds");
            throw new AdsValidationException("empty fields createAds");
        }
        Ads ads = new Ads();
        ads.setTitle(properties.getTitle());
        ads.setDescription(properties.getDescription());
        ads.setPrice(properties.getPrice());
        ads.setAuthor(userService.getUserByEmail(authentication.getName()));
        AdsImage newImage = adsImageService.saveImageFail(image, ads);
        ads.setImage(newImage);
        Ads updatedAds = adsRepository.save(ads);
        log.info("Successful save ads");
        return adsMapper.toDto(updatedAds);
    }

    private boolean validation(CreateAdsDto properties) {
        return (properties.getTitle().isEmpty()
                || properties.getDescription().isEmpty()
                || properties.getPrice() == null);
    }

    @Override
    public AdsDto updateAds(Integer id, CreateAdsDto createAds) {
        Ads ads = getAdsById(id);
        if (accessChecker.checkAccess(ads)) {
            if (validation(createAds)) {
                log.error("empty fields CreateAdsDto updateAds");
                throw new AdsValidationException("empty fields updateAds");
            }
            if (!ads.getTitle().equals(createAds.getTitle())) {
                adsImageService.copyImageFail(ads, createAds.getTitle());
            }
            ads.getImage().setNameAds(createAds.getTitle());
            ads.setTitle(createAds.getTitle());
            ads.setDescription(createAds.getDescription());
            ads.setPrice(createAds.getPrice());
            Ads savedAds = adsRepository.save(ads);
            log.info("Successful updating ads by id: {}", id);
            return adsMapper.toDto(savedAds);
        } else {
            return null;
        }
    }

    @Override
    public void updateAdsImage(Integer id, MultipartFile image) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new AdsValidationException("Ads not found"));
        AdsImage updatedImage = adsImageService.updateImageFail(image, ads.getImage(), ads);
        ads.setImage(updatedImage);
        adsRepository.save(ads);
    }


    @Override
    public ResponseWrapperAdsDto getAll() {
        log.info("Searching all ads");
        List<AdsDto> ads = adsRepository.findAllByOrderByIdDesc()
                .stream()
                .map(adsMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsDto(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAdsDto getAllByMe(Authentication authentication) {
        log.info("Searching all ads by author");
        List<AdsDto> ads = adsRepository.findAllByAuthorIdOrderByIdDesc
                        (userService.getUserByEmail(authentication.getName()).getId())
                .stream()
                .map(adsMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsDto(ads.size(), ads);
    }


    @Override
    public FullAdsDto getById(Integer id) {
        log.info("Searching by id: {}", id);
        return adsMapper.toFullAds(getAdsById(id));
    }

    @Override
    public void delete(Integer id) {
        Ads ads = getAdsById(id);
        if (accessChecker.checkAccess(ads)) {
            adsImageService.deleteImageFail(ads);
            adsRepository.delete(ads);
            log.info("Successful deleting ads by id: {}", id);
        }
    }

    @Override
    public Ads getAdsById(Integer id) {
        log.info("Searching ads by id: {}", id);
        if (id == null) {
            log.error("Ads id is null");
            throw new IllegalArgumentException("Ads id is null");
        }
        return adsRepository.findById(id).orElseThrow(() -> {
            log.error("Ads not found for id: {}", id);
            return new AdsValidationException("Ads not found");
        });
    }

    @Override
    public ResponseWrapperAdsDto getAll(String search) {
        log.info("Search all ads according to the query: {}", search);
        List<AdsDto> ads = adsRepository.findAllByTitleContainingIgnoreCase(search)
                .stream()
                .map(adsMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsDto(ads.size(), ads);
    }


}

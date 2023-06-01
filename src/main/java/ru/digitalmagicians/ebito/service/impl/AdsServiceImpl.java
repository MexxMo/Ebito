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
import ru.digitalmagicians.ebito.exception.AdsValidationException;
import ru.digitalmagicians.ebito.mapper.AdsMapper;
import ru.digitalmagicians.ebito.repository.AdsRepository;
import ru.digitalmagicians.ebito.service.AdsService;
import ru.digitalmagicians.ebito.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final UserService userService;

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
        adsRepository.save(ads);
//        log.info("Successful save ads: {}", ads);
        return AdsMapper.INSTANCE.toDto(ads);
    }

    private boolean validation(CreateAdsDto properties) {
        return (properties.getTitle().isEmpty()
                || properties.getDescription().isEmpty()
                || properties.getPrice() == null);
    }

    @Override
    public CreateAdsDto updateAds(Integer id, CreateAdsDto createAds) {
        if (validation(createAds)) {
            log.error("empty fields CreateAdsDto updateAds");
            throw new AdsValidationException("empty fields updateAds");
        }
        Ads ads = getAdsById(id);
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        adsRepository.save(ads);
        log.info("Successful updating ads by id: {}", id);
        return null;
    }

    @Override
    public void updateAdsImage(Integer id, MultipartFile image) {
        // todo
    }


    @Override
    public ResponseWrapperAdsDto getAll() {
        log.info("Searching all ads");
        List<AdsDto> ads = adsRepository.findAll().stream()
                .map(AdsMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return new ResponseWrapperAdsDto(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAdsDto getAllByMe(Authentication authentication) {
        log.info("Searching all ads by author");
        List<AdsDto> ads = adsRepository.findAllByAuthorId(userService.getUserByEmail(authentication.getName()).getId())
                .stream()
                .map(AdsMapper.INSTANCE::toDto)
                .collect(java.util.stream.Collectors.toList());
        return new ResponseWrapperAdsDto(ads.size(), ads);
    }


    @Override
    public FullAdsDto getById(Integer id) {
        log.info("Searching ads by id: {}", id);
        return AdsMapper.INSTANCE.toFullAds(getAdsById(id));
    }

    @Override
    public void delete(Integer id) {
        Ads ads = getAdsById(id);
        adsRepository.delete(ads);
        log.info("Successful deleting ads by id: {}", id);
    }
    @Override
    public Ads getAdsById(Integer id) {
        log.info("Searching ads by id: {}", id);
        if (id == null) {
            log.error("Ads id is null");
            throw new IllegalArgumentException("Ads id is null");
        }
        Optional<Ads> optionalAds = adsRepository.findById(id);
        if (optionalAds.isEmpty()) {
            log.error("Ads not found for id: {}", id);
            throw new AdsValidationException("Ads not found");
        }
        return optionalAds.get();
    }
}

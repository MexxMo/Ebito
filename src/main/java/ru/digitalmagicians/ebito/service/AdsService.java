package ru.digitalmagicians.ebito.service;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;

import java.util.List;


public interface AdsService {
    void createAds(MultipartFile image, CreateAdsDto properties,Authentication authentication);

   CreateAdsDto updateAds(Long id, CreateAdsDto adsDto);

   void updateAdsImage(Long id, MultipartFile image);

   List<AdsDto> getAll();

   List<AdsDto> getAllByMe(Authentication authentication);

    FullAdsDto getById(Long id);

   void delete();
}

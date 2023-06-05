package ru.digitalmagicians.ebito.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.CreateAdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.dto.ResponseWrapperAdsDto;


public interface AdsService {
    AdsDto createAds(MultipartFile image, CreateAdsDto properties, Authentication authentication);

    AdsDto updateAds(Integer id, CreateAdsDto adsDto);

    void updateAdsImage(Integer id, MultipartFile image);

    ResponseWrapperAdsDto getAll();

    ResponseWrapperAdsDto getAllByMe(Authentication authentication);

    FullAdsDto getById(Integer id);

    void delete(Integer id);

    Ads getAdsById(Integer id);
    ResponseWrapperAdsDto getAll(String search);
}

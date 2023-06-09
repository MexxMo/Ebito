package ru.digitalmagicians.ebito.service;

import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.AdsImage;

public interface AdsImageService {
    AdsImage saveImageFail(MultipartFile image, Ads ads);

    byte[] loadImageFail(String fileName);

    AdsImage updateImageFail(MultipartFile image, AdsImage adsImage);

    void deleteImageFailPath(String fileName);
}

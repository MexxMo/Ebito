package ru.digitalmagicians.ebito.service;

import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.AdsImage;

public interface AdsImageService {
    AdsImage saveImageFail(MultipartFile image, Ads ads);

    byte[] loadImageFail(String fileName);
    void copyImageFail( Ads ads,String title);

    AdsImage updateImageFail(MultipartFile image, AdsImage adsImage,Ads ads);
    void deleteImageFail(Ads ads);
}

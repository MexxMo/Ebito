package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.AdsImage;
import ru.digitalmagicians.ebito.repository.AdsImageRepository;
import ru.digitalmagicians.ebito.service.AdsImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.Files.*;
import static org.apache.catalina.startup.ExpandWar.delete;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdsImageServiceImpl implements AdsImageService {
    private final AdsImageRepository adsImageRepository;
    static String PATH = System.getProperty("user.dir") + "\\ads\\image";

    @Override
    public AdsImage saveImageFail(MultipartFile image, Ads ads) {
        BufferedImage bufferedImage;
        AdsImage adsImage = new AdsImage();
        try {
            String fileName = UUID.randomUUID().toString();
            adsImage.setId(fileName);
            adsImage.setType(type(image));
            adsImage.setNameAds(ads.getTitle());
            bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
            Files.createDirectories(Paths.get(path(ads.getTitle())));
            File outputfile = new File(path(ads.getTitle()), fileName + "." + type(image));
            ImageIO.write(bufferedImage, type(image), outputfile);
            log.info("Image file created by  name: {}", fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adsImage;
    }

    private String type(MultipartFile image) {
        String type = image.getContentType();
        assert type != null;
        type = type.replace("image/", "");
        return type;
    }

    private String path(String adsName) {
        return PATH + "\\" + adsName;
    }

    private AdsImage ads(String fileName) {
        Optional<AdsImage> ads = adsImageRepository.findById(fileName);
        if (ads.isEmpty()) {
            throw new RuntimeException("ads.isEmpty()");
        }
        return ads.get();
    }

    @Override
    public byte[] loadImageFail(String fileName) {
        AdsImage ads = ads(fileName);
        File image;
        byte[] outputFileBytes;
        try {
            image = new File(path(ads.getNameAds()), fileName + "." + ads.getType());
            outputFileBytes = Files.readAllBytes(image.toPath());
            log.info("File loaded successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputFileBytes;
    }

    @Override
    public AdsImage updateImageFail(MultipartFile image, AdsImage oldAdsImage, Ads ads) {
        deleteImageFail(ads);

        BufferedImage bufferedImage;
        try {
            Files.createDirectories(Paths.get(path(oldAdsImage.getNameAds())));
            oldAdsImage.setType(type(image));
            bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
            Files.createDirectories(Paths.get(path(oldAdsImage.getNameAds())));
            File outputfile = new File(path(ads.getTitle()), oldAdsImage.getId() + "." + type(image));
            ImageIO.write(bufferedImage, type(image), outputfile);
            log.info("File updated successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return oldAdsImage;
    }

    public void copyImageFail(Ads ads, String title) {

        Path sourcePath = Paths.get(path(ads.getTitle()), ads.getImage().getId() + "." + ads.getImage().getType());
        Path destinationPath = Paths.get(path(title), ads.getImage().getId() + "." + ads.getImage().getType());
        try {
            Files.createDirectories(Paths.get(path(title)));
            copy(sourcePath, destinationPath);
            deleteImageFail(ads);
            log.info("Copy file successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteImageFail(Ads ads) {

        Path path = Paths.get(path(ads.getTitle()), ads.getImage().getId() + "." + ads.getImage().getType());
        File file = new File(PATH, ads.getTitle());
        try {
            deleteIfExists(path);
            log.info("File deleted successfully");
            if (Objects.requireNonNull(file.list()).length == 0) {
                if (delete(file)) {
                    log.info("Directory deleted successfully");
                } else {
                    log.warn("Directory not deleted");
                }
            } else {
                log.info("File  exists");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

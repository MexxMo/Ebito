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
    static final String PATH = System.getProperty("user.dir") + "\\ads\\image";

    /**
     * Сохраняет изображение в фаловой системе
     *
     * @param image изображение объявления из фронтеда
     * @param ads   само объявление
     * @return изображение из объявления
     */

    @Override
    public AdsImage saveImageFail(MultipartFile image, Ads ads) {
        AdsImage adsImage = new AdsImage();
        try {
            String fileName = UUID.randomUUID().toString();
            adsImage.setId(fileName);
            adsImage.setType(type(image));
            adsImage.setNameAds(ads.getTitle());
            Files.createDirectories(Paths.get(path(ads.getTitle())));
            Path path = Paths.get(path(ads.getTitle()) +"\\"+ fileName + "." + type(image));
            Files.write(path, image.getBytes());
            log.info("Image file created by  name: {}", fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adsImage;
    }

    /**
     * Берет из MultipartFile расширение вида image/png
     * и удаляет image/
     *
     * @param image MultipartFile image из фронтеда
     * @return расширение файла в виде строки
     */

    private String type(MultipartFile image) {
        String type = image.getContentType();
        assert type != null;
        type = type.replace("image/", "");
        return type;
    }

    /**
     * Создаёт путь для фаловой системы
     *
     * @param adsName - название объявления
     * @return путь для фаловой системы
     */
    private String path(String adsName) {
        return PATH + "\\" + adsName;
    }

    /**
     * Находит изображение по названию объявления
     *
     * @param fileName название изображения
     * @return изображение по названию объявления
     */
    private AdsImage findImageFail(String fileName) {
        Optional<AdsImage> ads = adsImageRepository.findById(fileName);
        if (ads.isEmpty()) {
            throw new RuntimeException("ads.isEmpty()");
        }
        return ads.get();
    }

    /**
     * Загружает изображение по названию изображения
     *
     * @param fileName название изображения
     * @return изображение в виде byte[]
     */
    @Override
    public byte[] loadImageFail(String fileName) {
        AdsImage ads = findImageFail(fileName);
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

    /**
     * Обновляет изображение объявления и удаляет старое изображение
     *
     * @param image       изображение объявления из фронтед
     * @param oldAdsImage старое изображение объявления
     * @param ads         Объявление
     * @return обновленное изображение объявления
     */
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

    /**
     * При изменении названия объявления копирует старое изображение в новое значение
     * и удаляет старое значение
     *
     * @param ads   объявление
     * @param title название объявления
     */
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

    /**
     * Удаляет изображение объявления и каталог
     * Если после удаления изображения папка пустая, удаляет каталог
     *
     * @param ads объявление
     */
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

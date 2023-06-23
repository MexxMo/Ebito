package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Image;
import ru.digitalmagicians.ebito.service.ImageService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.Files.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final String desktopPath = System.getProperty("user.dir") + File.separator + "images";
    @Value("${default.image.url}")
    private final String url;


    @Override
    public Image saveImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            String fileName = UUID.randomUUID() + type(image);
            newImage.setId(fileName);
            createDirectories(Paths.get(desktopPath));
            image.transferTo(new File(desktopPath + File.separator + fileName));
            log.info("Image file created by  name: {}", fileName);
        } catch (IOException e) {
            log.error("Error while saving image file{}", newImage.getId());
        }
        return newImage;
    }


    @Override
    public byte[] loadImage(String fileName) {
        File image;
        byte[] outputFileBytes = null;
        try {
            image = new File(desktopPath, fileName);
            if (exists(image.toPath())) {
                outputFileBytes = readAllBytes(image.toPath());
                log.info("File loaded successfully");
            } else {
                try (InputStream in = new URL(url).openStream()) {
                    outputFileBytes = in.readAllBytes();
                    log.info("File loaded default successfully");
                }
            }
        } catch (IOException e) {
            log.error("file load error");
        }
        return outputFileBytes;
    }

    /**
     * Получает тип изображения
     *
     * @param image изображение из фронтед
     * @return тип данных
     */
    private String type(MultipartFile image) {
        String type = image.getContentType();
        assert type != null;
        type = type.replace("image/", ".");
        return type;
    }


    @Override
    public Image updateImage(MultipartFile image, Image oldImage) {
        Image newImage = new Image();
        try {
            String fileName = UUID.randomUUID() + type(image);
            createDirectories(Paths.get(desktopPath));
            deleteImage(oldImage);
            image.transferTo(new File(desktopPath + File.separator + fileName));
            newImage.setId(fileName);
            log.info("File updated successfully");
        } catch (IOException e) {
            log.error("Error while updating file {}", oldImage.getId());
        }
        return newImage;
    }

    @Override
    public void deleteImage(Image image) {

        Path path = Paths.get(desktopPath + File.separator + image.getId());
        try {
            deleteIfExists(path);
            log.info("File deleted successfully");
        } catch (IOException e) {
            log.error("Error while deleting file {}", image.getId());
        }
    }

}
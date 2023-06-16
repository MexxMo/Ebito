package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Image;
import ru.digitalmagicians.ebito.service.ImageService;

import java.io.File;
import java.io.IOException;
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


    @Override
    public Image saveImageFail(MultipartFile image) {
        Image adsImage = new Image();
        try {
            String fileName = UUID.randomUUID() + type(image);
            adsImage.setId(fileName);
            createDirectories(Paths.get(desktopPath));
            Path path = Paths.get(desktopPath + File.separator + fileName);
            write(path, image.getBytes());
            log.info("Image file created by  name: {}", fileName);
        } catch (IOException e) {
            log.error("Error while saving image file{}", adsImage.getId());
        }
        return adsImage;
    }


    @Override
    public byte[] loadImageFail(String fileName) {
        File image;
        byte[] outputFileBytes = null;
        try {
            image = new File(desktopPath, fileName);
            outputFileBytes = readAllBytes(image.toPath());
            log.info("File loaded successfully");
        } catch (IOException e) {
            log.error("Error while loading file {}", fileName);

        }
        return outputFileBytes;
    }

    /**
     * Получает тип изображения
     *
     * @param image изображение из фонтед
     * @return тип данных
     */
    private String type(MultipartFile image) {
        String type = image.getContentType();
        assert type != null;
        type = type.replace("image/", ".");
        return type;
    }


    @Override
    public Image updateImageFail(MultipartFile image, Image oldImage) {

        try {
            createDirectories(Paths.get(desktopPath));
            Path path = Paths.get(desktopPath + File.separator + oldImage.getId());
            deleteImageFail(oldImage);
            write(path, image.getBytes());
            log.info("File updated successfully");
        } catch (IOException e) {
            log.error("Error while updating file {}", oldImage.getId());
        }
        return oldImage;
    }

    @Override
    public void deleteImageFail(Image image) {

        Path path = Paths.get(desktopPath + File.separator + image.getId());
        try {
            deleteIfExists(path);
            log.info("File deleted successfully");
        } catch (IOException e) {
            log.error("Error while deleting file {}", image.getId());
        }
    }
}
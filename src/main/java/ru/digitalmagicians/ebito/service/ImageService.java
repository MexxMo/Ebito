package ru.digitalmagicians.ebito.service;

import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Image;

public interface ImageService {
    /**
     * Сохраняет изображение в файловой системе
     *
     * @param image изображение объявления из фронтеда
     * @return изображение из объявления
     */
    Image saveImage(MultipartFile image);

    /**
     * Загружает изображение по названию изображения
     *
     * @param fileName название изображения
     * @return изображение в виде byte[]
     */
    byte[] loadImage(String fileName);

    /**
     * Обновляет изображение объявления и удаляет старое изображение
     *
     * @param image    изображение объявления из фронтед
     * @param oldImage старое изображение объявления
     * @return обновленное изображение объявления
     */
    Image updateImage(MultipartFile image, Image oldImage);

    /**
     * Удаляет изображение объявления и каталог
     */
    void deleteImage(Image image);
}

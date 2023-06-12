package ru.digitalmagicians.ebito.service;


import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Image;
import ru.digitalmagicians.ebito.exception.ImageNotFoundException;

/**
 * Сервис для работы с изображениями.
 */
public interface ImageService {

    /**
     * Сохраняет новое изображение в базу данных.
     *
     * @param image файл изображения
     * @return сохраненное изображение
     */
    Image saveImage(MultipartFile image);

    /**
     * Обновляет существующее изображение в базе данных.
     *
     * @param newImage новый файл изображения
     * @param oldImage старое изображение
     * @return обновленное изображение
     */
    Image updateImage(MultipartFile newImage, Image oldImage);

    /**
     * Получает изображение по его идентификатору.
     *
     * @param id идентификатор изображения
     * @return файл изображения в виде массива байтов
     * @throws ImageNotFoundException если изображение не найдено в базе данных
     */
    byte[] getImageById(String id);

}


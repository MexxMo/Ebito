package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import ru.digitalmagicians.ebito.entity.Image;

import ru.digitalmagicians.ebito.repository.ImageRepository;

import ru.digitalmagicians.ebito.service.ImageService;

import java.io.*;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;


    @Override
    public Image saveImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            byte[] bytes = image.getBytes();
            newImage.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newImage.setId(UUID.randomUUID().toString());
        return imageRepository.saveAndFlush(newImage);
    }


    @Override
    public Image updateImage(MultipartFile image, Image oldImage) {
        try {
            byte[] bytes = image.getBytes();
            oldImage.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(oldImage);
    }



}
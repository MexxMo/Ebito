package ru.digitalmagicians.ebito.service;


import org.springframework.web.multipart.MultipartFile;
import ru.digitalmagicians.ebito.entity.Image;





public interface ImageService {


    Image saveImage(MultipartFile image);


    Image updateImage(MultipartFile newImage, Image oldImage);


}


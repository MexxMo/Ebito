package ru.digitalmagicians.ebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalmagicians.ebito.entity.Image;

public interface ImageRepository extends JpaRepository<Image, String> {


}

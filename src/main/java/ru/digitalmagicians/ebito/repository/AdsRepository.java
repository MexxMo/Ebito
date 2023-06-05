package ru.digitalmagicians.ebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalmagicians.ebito.entity.Ads;

import java.util.List;


public interface AdsRepository extends JpaRepository<Ads, Integer> {
    List<Ads> findAllByAuthorIdOrderByIdDesc(Integer id);
    List<Ads> findAllByOrderByIdDesc();
    List<Ads> findAllByTitleContainingIgnoreCase(String search);
}

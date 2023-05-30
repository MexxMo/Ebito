package ru.digitalmagicians.ebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalmagicians.ebito.entity.Ads;


public interface AdsRepository extends JpaRepository<Ads, Integer> {
}

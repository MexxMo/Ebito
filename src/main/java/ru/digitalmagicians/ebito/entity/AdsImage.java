package ru.digitalmagicians.ebito.entity;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ads_Image")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AdsImage {
    @Id
    private String id;
    @Column(name = "name_ads")
    private String nameAds;
    private String type;


}

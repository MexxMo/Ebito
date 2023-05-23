package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class Ads {
    private Integer pk;
    private Integer author;
    private String image;
    private Integer price;
    private String title;
}

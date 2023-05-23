package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class Ads {
    private int pk;
    private int author;
    private String image;
    private int price;
    private String title;
}

package ru.digitalmagicians.ebito.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private List<Ads> results;
}

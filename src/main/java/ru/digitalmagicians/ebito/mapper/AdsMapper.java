package ru.digitalmagicians.ebito.mapper;

import org.mapstruct.Mapper;


import org.mapstruct.Mapping;

import ru.digitalmagicians.ebito.dto.AdsDto;
import ru.digitalmagicians.ebito.dto.FullAdsDto;
import ru.digitalmagicians.ebito.entity.Ads;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "image.id")
    AdsDto toDto(Ads ads);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "image", source = "image.id")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "pk", source = "id")
    FullAdsDto toFullAds(Ads ads);
}

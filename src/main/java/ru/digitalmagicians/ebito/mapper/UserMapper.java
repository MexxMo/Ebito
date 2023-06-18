package ru.digitalmagicians.ebito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.digitalmagicians.ebito.dto.UserDto;
import ru.digitalmagicians.ebito.entity.Image;
import ru.digitalmagicians.ebito.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapper")
    UserDto toDto(User user);

    @Named("imageMapper")
    default String imageMapper(Image image) {
        if (image == null) {
            return null;
        }
        return "/users/image/" + image.getId();
    }
}

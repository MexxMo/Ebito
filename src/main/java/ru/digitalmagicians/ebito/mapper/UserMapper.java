package ru.digitalmagicians.ebito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.digitalmagicians.ebito.dto.UserDto;
import ru.digitalmagicians.ebito.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
@Mapping(target = "image", source = "image.id")
    UserDto toDto(User user);
}

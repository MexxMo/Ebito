package ru.digitalmagicians.ebito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.digitalmagicians.ebito.dto.UserDto;
import ru.digitalmagicians.ebito.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

   UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
}

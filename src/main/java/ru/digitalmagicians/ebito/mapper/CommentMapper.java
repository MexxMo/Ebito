package ru.digitalmagicians.ebito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    CommentMapper INSTANSE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "text", source = "text")
    CommentDto toDto(Comment entity);
}


package ru.digitalmagicians.ebito.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperCommentDto {
    private Integer count;
    private List<CommentDto> results;
}

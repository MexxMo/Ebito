package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer pk;
    private String text;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
}

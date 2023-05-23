package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class Comment {
    private Integer pk;
    private String text;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
}

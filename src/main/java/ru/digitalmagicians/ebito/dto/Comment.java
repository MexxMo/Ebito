package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class Comment {
    private int pk;
    private String text;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
}

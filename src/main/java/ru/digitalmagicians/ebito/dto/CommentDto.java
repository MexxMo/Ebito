package ru.digitalmagicians.ebito.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer pk; // id комментария
    private String text; // текст комментария
    private Integer author; // id автора комментария
    private String authorImage; // ссылка на аватар автора комментария
    private String authorFirstName; // имя создателя комментария
    private Integer createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
}

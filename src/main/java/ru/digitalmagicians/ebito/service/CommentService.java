package ru.digitalmagicians.ebito.service;

import org.springframework.security.core.Authentication;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.exception.IncorrectArgumentException;

import java.util.List;

/**
 * Сервис для работы с комментариями.
 */
public interface CommentService {
    /**
     * Метод для добавления комментария к объявлению.
     *
     * @param id               идентификатор объявления.
     * @param createCommentDto данные для создания комментария.
     * @param authentication   данные аутентификации пользователя.
     * @return добавленный комментарий в формате CommentDto.
     * @throws IncorrectArgumentException если текст комментария пустой.
     */
    CommentDto addComment(Integer id, CreateCommentDto createCommentDto, Authentication authentication);

    /**
     * Метод для удаления комментария по идентификатору объявления и комментария.
     *
     * @param adId      идентификатор объявления.
     * @param commentId идентификатор комментария.
     */
    void deleteComment(Integer adId, Integer commentId);

    /**
     * Метод для получения списка комментариев по идентификатору объявления.
     *
     * @param id идентификатор объявления.
     * @return список комментариев.
     */
    List<CommentDto> getComments(Integer id);

    /**
     * Метод для обновления комментария по идентификатору объявления и комментария.
     *
     * @param adId       идентификатор объявления.
     * @param commentId  идентификатор комментария.
     * @param CommentDto данные для обновления комментария.
     * @return обновленный комментарий в формате CommentDto, если обновление прошло успешно, иначе - null.
     * @throws IncorrectArgumentException если текст комментария пустой.
     */
    CommentDto updateComments(Integer adId, Integer commentId, CommentDto CommentDto);


}

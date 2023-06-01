package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.CommentDto;
import org.springframework.security.core.Authentication;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Integer id, CommentDto commentDto,
                             Authentication authentication);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComments(Integer adId, Integer commentId, CreateCommentDto createCommentDto);

    List<CommentDto> getComments(Integer id);

    Comment getCommentById(Integer id);
}

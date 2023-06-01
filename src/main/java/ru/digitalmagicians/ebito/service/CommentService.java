package ru.digitalmagicians.ebito.service;

import ru.digitalmagicians.ebito.dto.CommentDto;
import org.springframework.security.core.Authentication;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Integer id, CreateCommentDto createCommentDto,
                             Authentication authentication);

    void deleteComment(Integer adId, Integer commentId);

    CommentDto updateComments(Integer adId, Integer commentId, CommentDto commentDto);

    List<CommentDto> getComments(Integer id);

    Comment getCommentById(Integer id);
}

package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.entity.Comment;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.exception.*;
import ru.digitalmagicians.ebito.mapper.CommentMapper;
import ru.digitalmagicians.ebito.repository.CommentRepository;
import ru.digitalmagicians.ebito.service.AdsService;
import ru.digitalmagicians.ebito.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private AdsService adsService;
    private  CommentDto commentDto;


    @Override
    public List<CommentDto> getComments(Integer id) {
        return commentRepository.findAllByAdsId(id)
                .stream()
                .map(CommentMapper.INSTANCE::commentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Integer id, CreateCommentDto createCommentDto, Authentication authentication) {

        if(commentDto.getText() == null || commentDto.getText().isBlank()) throw new IncorrectArgumentException();

        Comment comment = new Comment();
        User user = (User) authentication.getPrincipal();

        comment.setAuthor(user);
        comment.setText(commentDto.getText());
        comment.setAds(adsService.getById(id.longValue()));
        comment.setCreatedAt(System.currentTimeMillis());
        commentRepository.save(comment);
        return CommentMapper.INSTANCE.INSTANCE.commentToDto(comment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = getComment(commentId, adId);
        commentRepository.delete(comment);
        log.info("Comment removed successfully");
    }

    @Override
    public CommentDto updateComments(Integer adId, Integer commentId,
                                        CommentDto adsCommentDto) {

        if(adsCommentDto.getText() == null || adsCommentDto.getText().isBlank()) throw new IncorrectArgumentException();

        Comment adsComment = getComment(commentId, adId);
        adsComment.setText(adsCommentDto.getText());
        commentRepository.save(adsComment);
        return CommentMapper.INSTANCE.commentToDto(adsComment);
    }

    public Comment getComment(Integer commentId, Integer adId) {
        return commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

}
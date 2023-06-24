package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.entity.Comment;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.exception.CommentNotFoundException;
import ru.digitalmagicians.ebito.exception.IncorrectArgumentException;
import ru.digitalmagicians.ebito.mapper.CommentMapper;
import ru.digitalmagicians.ebito.repository.CommentRepository;
import ru.digitalmagicians.ebito.security.AccessChecker;
import ru.digitalmagicians.ebito.service.AdsService;
import ru.digitalmagicians.ebito.service.CommentService;
import ru.digitalmagicians.ebito.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final AdsService adsService;
    private final CommentMapper commentMapper;
    private final AccessChecker accessChecker;


    @Override
    public List<CommentDto> getComments(Integer id) {
        return commentRepository.findAllByAdsId(id)
                .stream()
                .map(commentMapper::commentToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Integer id, CreateCommentDto createCommentDto, Authentication authentication) {
        if (createCommentDto.getText().isBlank()) {
            throw new IncorrectArgumentException();
        }

        Comment comment = new Comment();
        User user = userService.getUserByEmail(authentication.getName());
        comment.setAuthor(user);
        comment.setAds(adsService.getAdsById(id));
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setText(createCommentDto.getText());
        commentRepository.save(comment);
        log.info("Comment added id: {}", id);
        return commentMapper.commentToDto(comment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        Comment comment = getComment(adId, commentId);
        if (accessChecker.checkAccess(comment)) {
            commentRepository.delete(comment);
            log.info("Comment adId:{} commentID:{} removed successfully", adId, commentId);
        }
    }

    @Override
    public CommentDto updateComments(Integer adId, Integer commentId,
                                     CommentDto adsCommentDto) {
        Comment adsComment = getComment(adId, commentId);
        if (accessChecker.checkAccess(adsComment)) {
            if (adsCommentDto.getText().isBlank()) {
                throw new IncorrectArgumentException();
            }
            adsComment.setText(adsCommentDto.getText());
            commentRepository.save(adsComment);
            log.info("Comment update successfully adId:{}, commentId:{}", adId, commentId);
            return commentMapper.commentToDto(adsComment);
        } else {
            return null;
        }
    }

    public Comment getComment(Integer adId, Integer commentId) {
        return commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow(CommentNotFoundException::new);
    }
}
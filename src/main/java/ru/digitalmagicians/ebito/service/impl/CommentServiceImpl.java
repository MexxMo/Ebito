package ru.digitalmagicians.ebito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.entity.Comment;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.mapper.CommentMapper;
import ru.digitalmagicians.ebito.repository.CommentRepository;
import ru.digitalmagicians.ebito.service.CommentService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;
    //private final AdsServiceImpl adsService;

    @Override
    public List<CommentDto> getComments(Integer id) {
        log.debug("Getting comments for ads with id: {}", id);
        return commentRepository.findAllByAdsId(id)
                .stream()
                .map(CommentMapper.INSTANSE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) {
        log.debug("Adding comment for ads with id: {}", id);
        if(commentDto.getText() == null || commentDto.getText().isBlank()) throw new IncorrectArgumentException();

        Comment comment = CommentMapper.INSTANSE.toEntity(commentDto);
        User user = (User) authentication.getPrincipal();
        comment.setAuthor(user);
        comment.setAds(adsService.findAdsById(id));
        comment.setCreatedAt(Instant.now());
        commentRepository.save(comment);
        return CommentMapper.INSTANSE.toDto(comment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        log.debug("Deleting comment with id: {} for ads with id: {}", commentId, adId);
        Comment comment = getComment(commentId, adId);
        commentRepository.delete(comment);
        log.info("Comment removed successfully");
    }

    @Override
    public CommentDto updateComments(Integer adId, Integer commentId,
                                        CommentDto adsCommentDto) {
        log.debug("Updating comment with id: {} for ads with id: {}", commentId, adId);

        if(adsCommentDto.getText() == null || adsCommentDto.getText().isBlank()) throw new IncorrectArgumentException();

        Comment adsComment = getComment(commentId, adId);
        adsComment.setText(adsCommentDto.getText());
        commentRepository.save(adsComment);
        return CommentMapper.INSTANSE.toDto(adsComment);
    }

    public Comment getComment(Integer commentId, Integer adId) {
        log.debug("Getting comment with id: {} for ads with id: {}", commentId, adId);
        return commentRepository.findByIdAndAdsId(commentId, adId).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public Comment getCommentById(Integer id) {
        log.debug("Getting comment with id: {}", id);
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

}
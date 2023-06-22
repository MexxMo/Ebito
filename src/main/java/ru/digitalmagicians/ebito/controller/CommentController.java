package ru.digitalmagicians.ebito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.digitalmagicians.ebito.dto.CommentDto;
import ru.digitalmagicians.ebito.dto.CreateCommentDto;
import ru.digitalmagicians.ebito.dto.ResponseWrapperCommentDto;
import ru.digitalmagicians.ebito.service.CommentService;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @Operation(summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseWrapperCommentDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable("id") Integer id) {
        List<CommentDto> comments = service.getComments(id);
        return ResponseEntity.ok(new ResponseWrapperCommentDto(comments.size(), comments));
    }

    @Operation(summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer id,
                                                 @RequestBody CreateCommentDto createCommentDto,
                                                 Authentication authentication) {
        return ResponseEntity.ok(service.addComment(id, createCommentDto, authentication));
    }

    @Operation(summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("adId") Integer adId,
                                           @PathVariable("commentId") Integer commentId) {
        service.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId,
                                                    @PathVariable("commentId") Integer commentId,
                                                    @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(service.updateComments(adId, commentId, commentDto));
    }
}
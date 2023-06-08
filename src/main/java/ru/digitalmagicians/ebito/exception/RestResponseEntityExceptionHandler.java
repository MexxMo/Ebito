package ru.digitalmagicians.ebito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(AdsValidationException.class)
    public ResponseEntity<String> handleAdsValidationException(AdsValidationException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({CommentNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<?> handleNotFoundCommentException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(IncorrectArgumentException.class)
    public ResponseEntity<?> handleIncorrectArgumentException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<?> handlePermissionDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

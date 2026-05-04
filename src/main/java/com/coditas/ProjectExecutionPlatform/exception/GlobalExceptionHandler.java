package com.coditas.ProjectExecutionPlatform.exception;

import com.coditas.ProjectExecutionPlatform.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SprintNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSprintNotFoundException(SprintNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamMemberTaskLimitException.class)
    public ResponseEntity<ErrorResponse> handleTeamMemberTaskLimitException(TeamMemberTaskLimitException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

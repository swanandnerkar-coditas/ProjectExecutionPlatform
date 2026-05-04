package com.coditas.ProjectExecutionPlatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private LocalDateTime localDateTime;
}

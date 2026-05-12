package com.coditas.ProjectExecutionPlatform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeDTO {

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;
}

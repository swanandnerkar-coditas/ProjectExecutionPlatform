package com.coditas.ProjectExecutionPlatform.dto.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetEntryRequestDTO {

    @NotBlank
    private String timeSheetTaskDescription;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Long teamMemberId;
}

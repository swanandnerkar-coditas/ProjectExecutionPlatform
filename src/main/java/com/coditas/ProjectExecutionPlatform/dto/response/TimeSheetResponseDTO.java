package com.coditas.ProjectExecutionPlatform.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetResponseDTO {

    private Long timeSheetId;

    private String timeSheetTaskDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    // can add document Id as well or other DTO for document it any else null
}

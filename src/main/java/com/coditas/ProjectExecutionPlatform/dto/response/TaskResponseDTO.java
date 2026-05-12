package com.coditas.ProjectExecutionPlatform.dto.response;

import com.coditas.ProjectExecutionPlatform.enums.Priority;
import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskResponseDTO {
    private Long taskId;

    private Long sprintId;

//    private Long userId;

    private String description;

    private LocalDate dueDate;

    private TaskStatus taskStatus;

    private Priority priority;
}

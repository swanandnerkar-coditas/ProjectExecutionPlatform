package com.coditas.ProjectExecutionPlatform.dto.request;

import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusRequestDTO {

    @NotNull
    private Long taskId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus updatedTaskStatus;
}

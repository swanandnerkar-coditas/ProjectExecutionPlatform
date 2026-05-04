package com.coditas.ProjectExecutionPlatform.dto.request;

import com.coditas.ProjectExecutionPlatform.model.Sprint;
import com.coditas.ProjectExecutionPlatform.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotNull
    private Long sprintId;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate dueDate;
}

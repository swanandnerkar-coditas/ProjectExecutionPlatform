package com.coditas.ProjectExecutionPlatform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTaskRequestDTO {

    @NotNull
    private Long taskId;

    @NotNull
    private Long teamMemberId;
}

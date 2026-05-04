package com.coditas.ProjectExecutionPlatform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRegistrationRequestDTO {

    @NotBlank
    private String projectName;

    @NotNull
    private Long projectManagerId;

    @NotBlank
    private String projectDescription;

    @NotNull
    private LocalDate deadline;
}

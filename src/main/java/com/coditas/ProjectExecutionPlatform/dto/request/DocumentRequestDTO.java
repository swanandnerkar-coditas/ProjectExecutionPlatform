package com.coditas.ProjectExecutionPlatform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String githubLink;

    @NotNull
    private Long timeSheetEntryId;
}

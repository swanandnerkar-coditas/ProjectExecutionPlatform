package com.coditas.ProjectExecutionPlatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {

    private String name;

    private String githubLink;
}

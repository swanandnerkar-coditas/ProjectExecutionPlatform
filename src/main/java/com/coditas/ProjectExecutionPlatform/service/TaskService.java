package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    List<TaskResponseDTO> multiFilterSearch(FilterRequestDTO filterRequestDTO);
}

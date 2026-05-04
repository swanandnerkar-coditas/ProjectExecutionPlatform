package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.AssignTaskRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.SprintRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TaskRequestDTO;
import jakarta.validation.Valid;

public interface ProjectManagerService {
    String createSprint(@Valid SprintRequestDTO sprintRequestDTO);

    String createTask(@Valid TaskRequestDTO taskRequestDTO);

    String assignTask(@Valid AssignTaskRequestDTO assignTaskRequestDTO);
}

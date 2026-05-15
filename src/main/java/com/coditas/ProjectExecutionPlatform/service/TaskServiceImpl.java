package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;
import com.coditas.ProjectExecutionPlatform.exception.InvalidDueDateRangeException;
import com.coditas.ProjectExecutionPlatform.mapper.TaskMapper;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.Task;
import com.coditas.ProjectExecutionPlatform.model.User;
import com.coditas.ProjectExecutionPlatform.repository.ProjectRepository;
import com.coditas.ProjectExecutionPlatform.repository.TaskRepository;
import com.coditas.ProjectExecutionPlatform.repository.UserRepository;
import com.coditas.ProjectExecutionPlatform.utility.TaskSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskResponseDTO> multiFilterSearch(FilterRequestDTO filterRequestDTO) {
        Project project = null;
        User user = null;

        if(filterRequestDTO.getProjectId() != null)
            project = projectRepository.findById(filterRequestDTO.getProjectId()).orElse(null);

        if(filterRequestDTO.getTeamMemberId() != null)
            user = userRepository.findById(filterRequestDTO.getTeamMemberId()).orElse(null);

        LocalDate startDate = filterRequestDTO.getStartDate();
        LocalDate endDate = filterRequestDTO.getEndDate();
        if((filterRequestDTO.getStartDate() != null && filterRequestDTO.getEndDate() == null) || (filterRequestDTO.getStartDate() == null && filterRequestDTO.getEndDate() != null)){
            throw new InvalidDueDateRangeException("Enter correct Start date & End date, Check if both are present or not");
        }

        List<Task> tasks = taskRepository.findAll(TaskSpecificationBuilder.getSpecification(filterRequestDTO, project, user));

        return tasks.stream()
                .filter(Objects::nonNull)
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }
}

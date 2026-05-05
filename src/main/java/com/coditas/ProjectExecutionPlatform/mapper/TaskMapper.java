package com.coditas.ProjectExecutionPlatform.mapper;

import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;
import com.coditas.ProjectExecutionPlatform.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public static TaskResponseDTO toDto(Task task){
        return TaskResponseDTO.builder()
                .task_id(task.getTask_id())
                .sprintId(task.getSprint().getSprintId())
//                .userId(task.getUser().getId())
                .description(task.getDescription())
                .dueDate(task.getDueDate()).taskStatus(task.getTaskStatus())
                .priority(task.getPriority())
                .build();
    }
}

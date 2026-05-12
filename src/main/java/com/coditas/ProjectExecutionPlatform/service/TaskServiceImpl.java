package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;
import com.coditas.ProjectExecutionPlatform.enums.Priority;
import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import com.coditas.ProjectExecutionPlatform.exception.InvalidDueDateRangeException;
import com.coditas.ProjectExecutionPlatform.exception.ProjectNotFoundException;
import com.coditas.ProjectExecutionPlatform.exception.UserNotFoundException;
import com.coditas.ProjectExecutionPlatform.mapper.TaskMapper;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.Task;
import com.coditas.ProjectExecutionPlatform.model.User;
import com.coditas.ProjectExecutionPlatform.repository.ProjectRepository;
import com.coditas.ProjectExecutionPlatform.repository.TaskRepository;
import com.coditas.ProjectExecutionPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        List<Task> tasks = taskRepository.findAll();

//        int size = tasks.get(tasks.size()-1).getTask_id();
        int size = tasks.size();

        // or can choose max task id , but later
        int[] bucket = new int[size+1];

        int constraints = 0;

        Long projectId = filterRequestDTO.getProjectId();
        if(projectId != null){
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found for provided id"));

            tasks = taskRepository.findByProject(project);

            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        Long teamMemberId;
        if(filterRequestDTO.getTeamMemberId() != null){
            User user = userRepository.findById(filterRequestDTO.getTeamMemberId())
                    .orElseThrow(() -> new UserNotFoundException("User not found for provided Id"));

            tasks = taskRepository.findByUser(user);
            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        TaskStatus taskStatus;
        if(filterRequestDTO.getTaskStatus() != null){
            tasks = taskRepository.findByTaskStatus(filterRequestDTO.getTaskStatus());
            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        Priority priority;
        if(filterRequestDTO.getPriority() != null){
            tasks = taskRepository.findByPriority(filterRequestDTO.getPriority());
            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        LocalDate startDate = filterRequestDTO.getStartDate();
        LocalDate endDate = filterRequestDTO.getEndDate();
        if((filterRequestDTO.getStartDate() != null && filterRequestDTO.getEndDate() == null) || (filterRequestDTO.getStartDate() == null && filterRequestDTO.getEndDate() != null)){
            throw new InvalidDueDateRangeException("Enter correct Start date & End date, Check if both are present or not");
        }
        else if(filterRequestDTO.getStartDate() != null){
            tasks = taskRepository.findByDueDateBetween(startDate, endDate);
            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        String keyword;
        if(filterRequestDTO.getKeyword() != null){
            tasks = taskRepository.findByDescriptionContainingIgnoreCase(filterRequestDTO.getKeyword());
            for(Task task : tasks){
                int index = Math.toIntExact(task.getTaskId());
                bucket[index]++;
            }
            constraints++;
        }

        if(constraints != 0){
            tasks = new ArrayList<>();
            for(int i=1; i<bucket.length; i++){
                if(bucket[i] == constraints){
                    tasks.add(taskRepository.findById((long)i).orElse(null));
                }
            }
        }

        // if c == 0
        return tasks.stream()
                .filter(task -> task != null)
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }
}

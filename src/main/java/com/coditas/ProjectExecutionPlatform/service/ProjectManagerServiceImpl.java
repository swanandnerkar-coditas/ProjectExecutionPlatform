package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.AssignTaskRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.SprintRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TaskRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TaskStatusRequestDTO;
import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import com.coditas.ProjectExecutionPlatform.exception.*;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.Sprint;
import com.coditas.ProjectExecutionPlatform.model.Task;
import com.coditas.ProjectExecutionPlatform.model.User;
import com.coditas.ProjectExecutionPlatform.repository.ProjectRepository;
import com.coditas.ProjectExecutionPlatform.repository.SprintRepository;
import com.coditas.ProjectExecutionPlatform.repository.TaskRepository;
import com.coditas.ProjectExecutionPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectManagerServiceImpl implements ProjectManagerService{

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;

    @Override
    public String createSprint(SprintRequestDTO sprintRequestDTO) {

        Project project = projectRepository.findById(sprintRequestDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found for given ID"));

        Sprint sprint = Sprint.builder()
                .project(project)
                .startDate(sprintRequestDTO.getStartDate())
                .endDate(sprintRequestDTO.getEndDate())
                .duration(sprintRequestDTO.getDuration())
                .build();

        try{
            sprintRepository.save(sprint);

            List<Sprint> sprints = project.getSprints();
            sprints.add(sprint);
            project.setSprints(sprints);
            projectRepository.save(project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Sprint Created Successfully";
    }

    @Override
    public String createTask(TaskRequestDTO taskRequestDTO) {

        Sprint sprint = sprintRepository.findById(taskRequestDTO.getSprintId())
                .orElseThrow(() -> new SprintNotFoundException("Sprint not found for provided ID"));

        Task task = Task.builder()
                .sprint(sprint)
                .description(taskRequestDTO.getDescription())
                .dueDate(taskRequestDTO.getDueDate())
                .priority(taskRequestDTO.getPriority())
                .project(sprint.getProject())
                .taskStatus(TaskStatus.CREATED)
                .build();

        try{

            taskRepository.save(task);

            List<Task> tasks = sprint.getTasks();
            tasks.add(task);
            sprint.setTasks(tasks);
            sprintRepository.save(sprint);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Task Created Successfully";
    }

    @Override
    public String assignTask(AssignTaskRequestDTO assignTaskRequestDTO) {

        Task task = taskRepository.findById(assignTaskRequestDTO.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found for provided Id"));

        User user = userRepository.findById(assignTaskRequestDTO.getTeamMemberId())
                .orElseThrow(() -> new UserNotFoundException("User not found for provided Id"));

        try{
            List<Task> numberOfTasks = user.getTasks();
            if(numberOfTasks.size() == 2)
                throw new TeamMemberTaskLimitException("1 Team member can't have more than 2 task at a time");

            // silly logic : based on condition only 2 tasks so assuming first task is IN_PROGRESS
            if(numberOfTasks.size() == 1) {
                Task task1 = numberOfTasks.getFirst();
                task1.setTaskStatus(TaskStatus.IN_PROGRESS);
                taskRepository.save(task1);
            }

            numberOfTasks.add(task);
            user.setTasks(numberOfTasks);

            userRepository.save(user);

            task.setUser(user);
            task.setTaskStatus(TaskStatus.ASSIGNED);
            taskRepository.save(task);

        }
        catch (TeamMemberTaskLimitException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Task Assigned Successfully";
    }

    /*
        Updating Task Status by Project Manager
        Blocked : if they want
        Completed : when raise to check & verified to completed
        In progress : if not verified then again in progress
     */
    @Override
    public String updateTaskStatus(TaskStatusRequestDTO taskStatusRequestDTO) {

        try {
            Task task = taskRepository.findById(taskStatusRequestDTO.getTaskId())
                    .orElseThrow(() -> new TaskNotFoundException("Task not found for provided Id"));

            task.setTaskStatus(taskStatusRequestDTO.getUpdatedTaskStatus());

            taskRepository.save(task);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Task status updated successfully";
    }
}

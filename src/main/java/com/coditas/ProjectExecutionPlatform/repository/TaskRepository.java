package com.coditas.ProjectExecutionPlatform.repository;

import com.coditas.ProjectExecutionPlatform.enums.Priority;
import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.Task;
import com.coditas.ProjectExecutionPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    
    
    List<Task> findByDescriptionContainingIgnoreCase(String keyword);

    List<Task> findByPriority(Priority priority);

    List<Task> findByTaskStatus(TaskStatus taskStatus);

    List<Task> findByUser(User user);

    List<Task> findByProject(Project project);

    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
}

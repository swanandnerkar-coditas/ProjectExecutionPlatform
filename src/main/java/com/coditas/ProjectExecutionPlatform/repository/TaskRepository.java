package com.coditas.ProjectExecutionPlatform.repository;

import com.coditas.ProjectExecutionPlatform.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

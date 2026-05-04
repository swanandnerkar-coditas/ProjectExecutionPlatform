package com.coditas.ProjectExecutionPlatform.repository;

import com.coditas.ProjectExecutionPlatform.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}

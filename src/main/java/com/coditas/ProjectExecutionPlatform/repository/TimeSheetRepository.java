package com.coditas.ProjectExecutionPlatform.repository;

import com.coditas.ProjectExecutionPlatform.model.TimeSheetEntry;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntry, Long> {
    List<TimeSheetEntry> findByStartTimeGreaterThanAndEndTimeLessThan(@NotNull LocalDateTime startDateTime, @NotNull LocalDateTime endDateTime);
}

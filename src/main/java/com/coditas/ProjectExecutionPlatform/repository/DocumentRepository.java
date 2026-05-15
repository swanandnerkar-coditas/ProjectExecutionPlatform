package com.coditas.ProjectExecutionPlatform.repository;

import com.coditas.ProjectExecutionPlatform.model.Document;
import com.coditas.ProjectExecutionPlatform.model.TimeSheetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByTimeSheetEntry(TimeSheetEntry timeSheetEntry);

    Optional<Document> findByFileName(String s);

    List<Document> findByFileNameContaining(String s);
}

package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface DocumentService {

    List<DocumentResponseDTO> searchDocuments();

    String deleteDocument(LocalDate date, String fileName) throws IOException;

    String uploadDocument(MultipartFile multipartFile, Long timeSheetEntryId) throws IOException;

    String downloadDocuments(LocalDate date, String fileName);
}

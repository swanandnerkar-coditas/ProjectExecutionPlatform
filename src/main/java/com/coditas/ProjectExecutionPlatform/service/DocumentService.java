package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
    String createDocument(DocumentRequestDTO documentRequestDTO);

    DocumentResponseDTO searchDocuments(Long entryId);

    String deleteDocument(String fileName) throws IOException;

    String uploadDocument(MultipartFile multipartFile) throws IOException;

    String downloadDocuments(String fileName);
}

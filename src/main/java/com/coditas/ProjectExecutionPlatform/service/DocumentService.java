package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;

public interface DocumentService {
    String createDocument(DocumentRequestDTO documentRequestDTO);

    DocumentResponseDTO searchDocuments(Long entryId);

    String deleteDocument(Long entryId);
}

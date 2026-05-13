package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TimeSheetEntryRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;
import com.coditas.ProjectExecutionPlatform.service.DocumentService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    ResponseEntity<String> createDocument(@Valid @RequestBody DocumentRequestDTO documentRequestDTO){
        String response = documentService.createDocument(documentRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{entryId}")
    ResponseEntity<DocumentResponseDTO> searchDocuments(@PathVariable Long entryId ){
        DocumentResponseDTO documents = documentService.searchDocuments(entryId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("{entryId}")
    ResponseEntity<String> deleteDocument(@PathVariable Long entryId){
        String response = documentService.deleteDocument(entryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

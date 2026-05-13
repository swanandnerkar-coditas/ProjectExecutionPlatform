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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

//    @PostMapping
//    ResponseEntity<String> createDocument(@Valid @RequestBody DocumentRequestDTO documentRequestDTO){
//        String response = documentService.createDocument(documentRequestDTO);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    // new with Multipart File
    @PostMapping("/upload")
    ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String response = documentService.uploadDocument(multipartFile);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{entryId}")
    ResponseEntity<DocumentResponseDTO> searchDocuments(@PathVariable Long entryId ){
        DocumentResponseDTO documents = documentService.searchDocuments(entryId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    ResponseEntity<String> deleteDocument(@PathVariable String fileName) throws IOException {
        String response = documentService.deleteDocument(fileName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<String> downloadDocuments(@PathVariable String fileName ){
        String path = documentService.downloadDocuments(fileName);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}

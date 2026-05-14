package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import com.coditas.ProjectExecutionPlatform.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload/{id}")
    ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") Long timeSheetEntryId) throws IOException {
        String response = documentService.uploadDocument(multipartFile, timeSheetEntryId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<DocumentResponseDTO>> searchDocuments(){
        List<DocumentResponseDTO> documents = documentService.searchDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{date}/{fileName}")
    ResponseEntity<String> deleteDocument(@PathVariable LocalDate date, @PathVariable String fileName) throws IOException {
        String response = documentService.deleteDocument(date, fileName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{date}/{fileName}")
    ResponseEntity<String> downloadDocuments(@PathVariable LocalDate date, @PathVariable String fileName ){
        String path = documentService.downloadDocuments(date, fileName);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}

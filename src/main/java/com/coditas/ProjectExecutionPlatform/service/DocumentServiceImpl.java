package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import com.coditas.ProjectExecutionPlatform.exception.DocumentNotFoundException;
import com.coditas.ProjectExecutionPlatform.exception.TimeSheetEntryNotFoundException;
import com.coditas.ProjectExecutionPlatform.model.Document;
import com.coditas.ProjectExecutionPlatform.model.TimeSheetEntry;
import com.coditas.ProjectExecutionPlatform.repository.DocumentRepository;
import com.coditas.ProjectExecutionPlatform.repository.TimeSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository documentRepository;
    private final TimeSheetRepository timeSheetRepository;

    /*
        verify whether entry id valid
        then assign that id to document
     */
    @Override
    public String createDocument(DocumentRequestDTO documentRequestDTO) {

        Long entryId = documentRequestDTO.getTimeSheetEntryId();
        TimeSheetEntry timeSheetEntry = timeSheetRepository.findById(entryId)
                .orElseThrow(() -> new TimeSheetEntryNotFoundException("Provided Time sheet entry not found"));

        Document document = Document.builder()
                .name(documentRequestDTO.getName())
                .githubLink(documentRequestDTO.getGithubLink())
                .timeSheetEntry(timeSheetEntry)
                .build();
        try{
            documentRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Document Submitted Successfully for Time Sheet Entry : "+entryId;
    }

    @Override
    public DocumentResponseDTO searchDocuments(Long entryId) {

        TimeSheetEntry timeSheetEntry = timeSheetRepository.findById(entryId)
                .orElseThrow(() -> new TimeSheetEntryNotFoundException("Provided Time sheet entry not found"));

        Document document = documentRepository.findByTimeSheetEntry(timeSheetEntry)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found for provided time sheet entry"));

        return DocumentResponseDTO.builder()
                .name(document.getName())
                .githubLink(document.getGithubLink())
                .build();
    }

    @Override
    public String deleteDocument(Long entryId) {

        TimeSheetEntry timeSheetEntry = timeSheetRepository.findById(entryId)
                .orElseThrow(() -> new TimeSheetEntryNotFoundException("Provided Time sheet entry not found"));

        Document document = documentRepository.findByTimeSheetEntry(timeSheetEntry)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found for provided time sheet entry"));

        try{
            timeSheetEntry.setDocument(null);
            documentRepository.delete(document);
            timeSheetRepository.save(timeSheetEntry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Document Deleted Successfully";
    }
}

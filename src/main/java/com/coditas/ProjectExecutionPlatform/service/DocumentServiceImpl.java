package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DocumentRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.DocumentResponseDTO;
import com.coditas.ProjectExecutionPlatform.exception.DocumentNotFoundException;
import com.coditas.ProjectExecutionPlatform.exception.FileFormatNotSupportedException;
import com.coditas.ProjectExecutionPlatform.exception.TimeSheetEntryNotFoundException;
import com.coditas.ProjectExecutionPlatform.model.Document;
import com.coditas.ProjectExecutionPlatform.model.TimeSheetEntry;
import com.coditas.ProjectExecutionPlatform.repository.DocumentRepository;
import com.coditas.ProjectExecutionPlatform.repository.TimeSheetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository documentRepository;
    private final TimeSheetRepository timeSheetRepository;

    private final String uploadDir = "src/main/resources";
    private static final List<String> validFileFormats = List.of(".txt", ".png", ".pdf");

    // Old logic
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

    /*
        create folder for each new date
        if already present then add in that one only
     */
    @Override
    public String uploadDocument(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();
        String fileType = multipartFile.getContentType();
        long size = multipartFile.getSize();

        log.info("File Type : "+ fileType);
        int index = multipartFile.getOriginalFilename().lastIndexOf('.');
        if(index > 0){
            String format = fileName.substring(index);
            if(!validFileFormats.contains(format)) throw new FileFormatNotSupportedException(fileType+" not supported !");
        }

        String dailyFolder = String.valueOf(LocalDate.now());

        Path path = Paths.get(uploadDir+"/"+dailyFolder, fileName);
        Files.createDirectories(path.getParent());
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return "File / Documents Saved Successfully";
    }

    @Override
    public String downloadDocuments(String fileName) {

        Path path = Paths.get(uploadDir).resolve(fileName).toAbsolutePath();
        return path.toString();
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
    public String deleteDocument(String fileName) throws IOException {

//        Path path = Paths.get(uploadDir).resolve(fileName).toAbsolutePath().normalize();

        Path path = Paths.get(uploadDir, fileName);

        if(Files.exists(path)){
            Files.delete(path);
            return "Document Deleted Successfully";
        }

        throw new FileNotFoundException("File / Document not found");
    }


}

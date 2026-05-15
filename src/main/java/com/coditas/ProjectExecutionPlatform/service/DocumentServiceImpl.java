package com.coditas.ProjectExecutionPlatform.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository documentRepository;
    private final TimeSheetRepository timeSheetRepository;

    private final String uploadDir = "src/main/resources/";
    private static final List<String> validFileFormats = List.of(".txt", ".png", ".pdf");


    @Override
    @Transactional
    public String uploadDocument(MultipartFile multipartFile, Long timeSheetEntryId) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileType = multipartFile.getContentType();
            long size = multipartFile.getSize();

            TimeSheetEntry timeSheetEntry = timeSheetRepository.findById(timeSheetEntryId)
                    .orElseThrow(() -> new TimeSheetEntryNotFoundException("Time Sheet Entry not found exception"));

            log.info("File Type : " + fileType);
            int index = multipartFile.getOriginalFilename().lastIndexOf('.');
            if (index > 0) {
                String format = fileName.substring(index);
                if (!validFileFormats.contains(format))
                    throw new FileFormatNotSupportedException(fileType + " not supported !");
            }

            String dailyFolder = String.valueOf(LocalDate.now());

            Path path = Paths.get(uploadDir + "/" + dailyFolder, fileName);
            Files.createDirectories(path.getParent());
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Document document = Document.builder()
                    .fileName(fileName +"_"+ LocalDateTime.now())
                    .fileAddress(String.valueOf(path.toAbsolutePath()))
                    .timeSheetEntry(timeSheetEntry)
                    .build();

            documentRepository.save(document);
        }
        catch (TimeSheetEntryNotFoundException e) {
            throw e;
        }

        return "File / Documents Saved Successfully";
    }

    @Override
    public String downloadDocuments(LocalDate date, String fileName) {

        Path path = Paths.get(uploadDir+"/"+date).resolve(fileName).toAbsolutePath();
        return path.toString();
    }

    @Override
    public List<DocumentResponseDTO> searchDateWiseDocuments(LocalDate date) {

        List<Document> documents = documentRepository.findByFileNameContaining(String.valueOf(date));
        if(documents == null)
            return List.of();

        List<DocumentResponseDTO> documentResponseDTOS = new ArrayList<>();
        for(Document document : documents) {
            DocumentResponseDTO documentResponseDTO = DocumentResponseDTO.builder()
                    .fileName(document.getFileName())
                    .fileAddress(document.getFileAddress())
                    .build();
            documentResponseDTOS.add(documentResponseDTO);
        }

        return documentResponseDTOS;
    }

    @Override
    public List<DocumentResponseDTO> searchDocuments() {

        List<Document> documents = documentRepository.findAll();

        List<DocumentResponseDTO> documentResponseDTOS = new ArrayList<>();
        for(Document document : documents) {
            DocumentResponseDTO documentResponseDTO = DocumentResponseDTO.builder()
                    .fileName(document.getFileName())
                    .fileAddress(document.getFileAddress())
                    .build();
            documentResponseDTOS.add(documentResponseDTO);
        }

        return documentResponseDTOS;
    }

    @Override
    @Transactional
    public String deleteDocument(LocalDate date, String fileName) throws IOException {

        Path path = Paths.get(uploadDir+"/"+date).resolve(fileName).toAbsolutePath().normalize();

        try{
            if(!Files.exists(path)) {
                throw new FileNotFoundException("File / Document not found");
            }

            Document document = documentRepository.findByFileName(fileName+"_"+date)
                    .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

            TimeSheetEntry timeSheetEntry =  document.getTimeSheetEntry();
            timeSheetEntry.setDocument(null);

            timeSheetRepository.save(timeSheetEntry);
            documentRepository.delete(document);

            log.info("Building file name form date & name ------> "+fileName+"_"+date);
            log.info("Actual file name : "+document.getFileName());
            log.info("checking whether both are equal or not : "+ (document.getFileName().equals(fileName+"_"+date)));

            Files.delete(path);

            return "Document Deleted Successfully";
        } catch (DocumentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

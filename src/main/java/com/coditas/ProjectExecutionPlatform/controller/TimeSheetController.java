package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.DateRangeDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TimeSheetEntryRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TimeSheetResponseDTO;
import com.coditas.ProjectExecutionPlatform.service.TimeSheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/time-sheet")
@RequiredArgsConstructor
public class TimeSheetController {

    private final TimeSheetService timeSheetService;

    @PostMapping
    ResponseEntity<String> createTimeSheetEntry(@Valid @RequestBody TimeSheetEntryRequestDTO timeSheetEntryRequestDTO){
        String response = timeSheetService.createTimeSheetEntry(timeSheetEntryRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("date-range")
    ResponseEntity<List<TimeSheetResponseDTO>> getEntriesByDateRange(@RequestBody DateRangeDTO dateRangeDTO){
        List<TimeSheetResponseDTO> timeSheetEntries = timeSheetService.getEntriesByDateRange(dateRangeDTO);
        return new ResponseEntity<>(timeSheetEntries, HttpStatus.OK);
    }
}

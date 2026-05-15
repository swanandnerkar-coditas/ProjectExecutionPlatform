package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DateRangeDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TimeSheetEntryRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TimeSheetResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface TimeSheetService {
    String createTimeSheetEntry(@Valid TimeSheetEntryRequestDTO timeSheetEntryRequestDTO);

    List<TimeSheetResponseDTO> getEntriesByDateRange(DateRangeDTO dateRangeDTO);
}

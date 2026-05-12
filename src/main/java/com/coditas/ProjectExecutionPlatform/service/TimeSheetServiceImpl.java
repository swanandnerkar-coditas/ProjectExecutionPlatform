package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.DateRangeDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TimeSheetEntryRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TimeSheetResponseDTO;
import com.coditas.ProjectExecutionPlatform.exception.InvalidDateTimeException;
import com.coditas.ProjectExecutionPlatform.exception.UserNotFoundException;
import com.coditas.ProjectExecutionPlatform.model.TimeSheetEntry;
import com.coditas.ProjectExecutionPlatform.model.User;
import com.coditas.ProjectExecutionPlatform.repository.TimeSheetRepository;
import com.coditas.ProjectExecutionPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSheetServiceImpl implements TimeSheetService{

    private final TimeSheetRepository timeSheetRepository;
    private final UserRepository userRepository;

    @Override
    public String createTimeSheetEntry(TimeSheetEntryRequestDTO timeSheetEntryRequestDTO) {

        User user = userRepository.findById(timeSheetEntryRequestDTO.getTeamMemberId())
                .orElseThrow(() -> new UserNotFoundException("User not found for provided Id"));

        TimeSheetEntry timeSheetEntry = TimeSheetEntry.builder()
                .timeSheetTaskDescription(timeSheetEntryRequestDTO.getTimeSheetTaskDescription())
                .startTime(timeSheetEntryRequestDTO.getStartTime())
                .endTime(timeSheetEntryRequestDTO.getEndTime())
                .user(user)
                .build();

        if(timeSheetEntry.getStartTime().isAfter(timeSheetEntry.getEndTime()))
            throw new InvalidDateTimeException("Check Start date time & End date time ");

        timeSheetRepository.save(timeSheetEntry);

        return "Time Sheet Entry entered Successfully";
    }

    @Override
    public List<TimeSheetResponseDTO> getEntriesByDateRange(DateRangeDTO dateRangeDTO) {

        if(dateRangeDTO.getStartDateTime().isAfter(dateRangeDTO.getEndDateTime()))
            throw new InvalidDateTimeException("Check Start date time & End date");

        List<TimeSheetResponseDTO> timeSheetResponseDTOS = new ArrayList<>();
        try{
            List<TimeSheetEntry> timeSheetEntryList = timeSheetRepository.findByStartTimeGreaterThanAndEndTimeLessThan(dateRangeDTO.getStartDateTime(), dateRangeDTO.getEndDateTime());

            for(TimeSheetEntry timeSheetEntry : timeSheetEntryList){
                TimeSheetResponseDTO timeSheetResponseDTO = TimeSheetResponseDTO.builder()
                        .timeSheetId(timeSheetEntry.getTimeSheetId())
                        .timeSheetTaskDescription(timeSheetEntry.getTimeSheetTaskDescription())
                        .startTime(timeSheetEntry.getStartTime())
                        .endTime(timeSheetEntry.getEndTime())
                        .build();
                timeSheetResponseDTOS.add(timeSheetResponseDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return timeSheetResponseDTOS;
    }
}

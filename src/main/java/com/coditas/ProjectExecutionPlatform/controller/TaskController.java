package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.TaskResponseDTO;
import com.coditas.ProjectExecutionPlatform.service.TaskService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/filter")
    ResponseEntity<List<TaskResponseDTO>> multiFilterSearch(@RequestBody @Nullable FilterRequestDTO filterRequestDTO){
        log.info("Task status: {}", filterRequestDTO.getTaskStatus());
        List<TaskResponseDTO> tasks = taskService.multiFilterSearch(filterRequestDTO);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}

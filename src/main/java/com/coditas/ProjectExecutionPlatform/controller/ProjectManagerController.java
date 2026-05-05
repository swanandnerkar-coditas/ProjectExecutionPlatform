package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.*;
import com.coditas.ProjectExecutionPlatform.service.ProjectManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project-manager")
@RequiredArgsConstructor
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;

    @PostMapping("/sprint")
    ResponseEntity<String> createSprint(@Valid @RequestBody SprintRequestDTO sprintRequestDTO){
        // will use DTO everywhere , doing with main functionality first
        String response = projectManagerService.createSprint(sprintRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/task")
    ResponseEntity<String> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO){
        String response = projectManagerService.createTask(taskRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/assign-task")
    ResponseEntity<String> assignTask(@Valid @RequestBody AssignTaskRequestDTO assignTaskRequestDTO){
        String response = projectManagerService.assignTask(assignTaskRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/task-status")
    ResponseEntity<String> updateTaskStatus(@Valid @RequestBody TaskStatusRequestDTO taskStatusRequestDTO){
        String response = projectManagerService.updateTaskStatus(taskStatusRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

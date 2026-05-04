package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.AssignTaskRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.ProjectRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.SprintRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.TaskRequestDTO;
import com.coditas.ProjectExecutionPlatform.service.ProjectManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;

    @PostMapping("/sprint")
    ResponseEntity<String> registerUser(@Valid @RequestBody SprintRequestDTO sprintRequestDTO){
        // will use DTO everywhere , doing with main functionality first
        String response = projectManagerService.createSprint(sprintRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/task")
    ResponseEntity<String> registerUser(@Valid @RequestBody TaskRequestDTO taskRequestDTO){
        String response = projectManagerService.createTask(taskRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/assign-task")
    ResponseEntity<String> registerUser(@Valid @RequestBody AssignTaskRequestDTO assignTaskRequestDTO){
        String response = projectManagerService.assignTask(assignTaskRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

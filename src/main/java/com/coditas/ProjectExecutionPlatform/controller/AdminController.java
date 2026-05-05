package com.coditas.ProjectExecutionPlatform.controller;

import com.coditas.ProjectExecutionPlatform.dto.request.ProjectRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.UserRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.UserRegistrationResponseDTO;
import com.coditas.ProjectExecutionPlatform.service.AdminService;
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
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/user")
    ResponseEntity<UserRegistrationResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO){
        UserRegistrationResponseDTO response = adminService.registerUser(userRegistrationRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/project")
    ResponseEntity<String> createProject(@Valid @RequestBody ProjectRegistrationRequestDTO projectRegistrationRequestDTO){
        // will use DTO everywhere , doing with main functionality first
        String response = adminService.createProject(projectRegistrationRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

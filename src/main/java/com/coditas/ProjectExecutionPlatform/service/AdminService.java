package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.ProjectRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.UserRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.UserRegistrationResponseDTO;
import jakarta.validation.Valid;

public interface AdminService {
    UserRegistrationResponseDTO registerUser(@Valid UserRegistrationRequestDTO userRegistrationRequestDTO);

    String createProject(@Valid ProjectRegistrationRequestDTO projectRegistrationRequestDTO);
}

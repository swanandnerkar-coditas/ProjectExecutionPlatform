package com.coditas.ProjectExecutionPlatform.service;

import com.coditas.ProjectExecutionPlatform.dto.request.ProjectRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.request.UserRegistrationRequestDTO;
import com.coditas.ProjectExecutionPlatform.dto.response.UserRegistrationResponseDTO;
import com.coditas.ProjectExecutionPlatform.exception.UserNotFoundException;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.User;
import com.coditas.ProjectExecutionPlatform.repository.ProjectRepository;
import com.coditas.ProjectExecutionPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {

        User user = User.builder()
                .username(userRegistrationRequestDTO.getUsername())
                .name(userRegistrationRequestDTO.getName())
                .email(userRegistrationRequestDTO.getEmail())
                .password(userRegistrationRequestDTO.getPassword())
                .role(userRegistrationRequestDTO.getRole())
                .experience(userRegistrationRequestDTO.getExperience())
                .build();

        try{
            userRepository.save(user);
        } catch (Exception e) {
            // have to handle gracefully : later
            throw new RuntimeException(e);
        }
        return new UserRegistrationResponseDTO("User Created Successfully");
    }

    @Override
    public String createProject(ProjectRegistrationRequestDTO projectRegistrationRequestDTO) {

        User user = userRepository.findById(projectRegistrationRequestDTO.getProjectManagerId())
                .orElseThrow(() -> new UserNotFoundException("User not found for provided Id"));

        Project project = Project.builder()
                .projectName(projectRegistrationRequestDTO.getProjectName())
                .user(user)
                .projectDescription(projectRegistrationRequestDTO.getProjectDescription())
                .deadline(projectRegistrationRequestDTO.getDeadline())
                .build();

        try{
            projectRepository.save(project);

            List<Project> projects = user.getProject();
            projects.add(project);
            user.setProject(projects);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Project Created Successfully";
    }
}

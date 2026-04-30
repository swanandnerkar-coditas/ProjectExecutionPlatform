package com.coditas.ProjectExecutionPlatform.model;

import com.coditas.ProjectExecutionPlatform.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
    private Integer experience;

    @OneToOne(mappedBy = "user")
    private Task task;

    @OneToOne(mappedBy = "user")
    private Project project;
}

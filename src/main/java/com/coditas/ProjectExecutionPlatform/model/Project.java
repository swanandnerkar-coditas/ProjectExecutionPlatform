package com.coditas.ProjectExecutionPlatform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private User user;

    @Column(name = "project_description")
    private String projectDescription;

    private LocalDate deadline;

    @OneToMany(mappedBy = "project")
    private List<Sprint> sprints;

}

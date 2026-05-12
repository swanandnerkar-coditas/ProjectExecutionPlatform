package com.coditas.ProjectExecutionPlatform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(nullable = false)
    private String name;

    @Column(name = "github_link", nullable = false)
    private String githubLink;

    @OneToOne
    @JoinColumn(name = "time_sheet_entry")
    private TimeSheetEntry timeSheetEntry;
}

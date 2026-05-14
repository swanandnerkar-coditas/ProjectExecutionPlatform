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

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_address", nullable = false)
    private String fileAddress;

    @OneToOne
    @JoinColumn(name = "time_sheet_entry")
    private TimeSheetEntry timeSheetEntry;
}

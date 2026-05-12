package com.coditas.ProjectExecutionPlatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_sheet_entries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_sheet_id")
    private Long timeSheetId;

    @Column(nullable = false)
    private String timeSheetTaskDescription;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @OneToOne(mappedBy = "timeSheetEntry")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "team_memeber_id")
    private User user;
}

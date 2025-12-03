package com.bm.education.feature.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdaptationProgramDto {
    private Long id;
    private String title;
    private String description;
    private Integer assignedToUserId;
    private Integer mentorUserId;
    private ProgramStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant createdAt;
    private Instant updatedAt;

    public enum ProgramStatus {
        NEW,
        IN_PROGRESS,
        COMPLETED
    }
}
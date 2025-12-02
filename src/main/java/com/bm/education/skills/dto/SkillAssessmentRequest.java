package com.bm.education.skills.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SkillAssessmentRequest {
    private Long skillId;
    private Integer proficiencyLevel; // 0-5 scale
    private String assessmentMethod; // SELF, MANAGER, TEST
    private Instant lastAssessed;
}
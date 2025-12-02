package com.bm.education.skills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillDto {
    private Long id;
    private Long userId;
    private Long skillId;
    private String skillName;
    private Integer proficiencyLevel;
    private Instant assessedAt;
}

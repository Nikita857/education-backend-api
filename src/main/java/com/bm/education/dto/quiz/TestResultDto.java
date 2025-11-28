package com.bm.education.dto.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TestResultDto {
    private Long id;
    private Long testId;
    private Integer userId;
    private Integer score;
    private Integer maxScore;
    private Double percentage;
    private Boolean passed;
    private Instant completedAt;

    public TestResultDto(Long id, Long testId, Integer userId, Integer score, Integer maxScore, Double percentage, Boolean passed, Instant completedAt) {
        this.id = id;
        this.testId = testId;
        this.userId = userId;
        this.score = score;
        this.maxScore = maxScore;
        this.percentage = percentage;
        this.passed = passed;
        this.completedAt = completedAt;
    }
}

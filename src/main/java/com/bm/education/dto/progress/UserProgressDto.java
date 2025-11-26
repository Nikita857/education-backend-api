package com.bm.education.dto.progress;

import lombok.Data;

import java.time.Instant;

@Data
public class UserProgressDto {
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Integer moduleId;
    private Integer lessonId;
    private Instant completedAt;
}
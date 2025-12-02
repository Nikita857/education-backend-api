package com.bm.education.learning.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserCoursesDto {
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Instant enrolledAt;
}
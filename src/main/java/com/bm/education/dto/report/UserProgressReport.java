package com.bm.education.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressReport {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String department;
    private String jobTitle;
    private Integer courseId;
    private String courseTitle;
    private Integer moduleId;
    private String moduleTitle;
    private Integer lessonId;
    private String lessonTitle;
    private Instant completedAt;
}

package com.bm.education.feature.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LearnerDashboardDto {
    private Integer activeCoursesCount;
    private Integer completedCoursesCount;
    private Integer certificatesCount;
    private List<String> recentActivities;
}

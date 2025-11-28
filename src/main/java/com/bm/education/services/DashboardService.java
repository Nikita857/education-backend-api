package com.bm.education.services;

import com.bm.education.dto.dashboard.InstructorDashboardDto;
import com.bm.education.dto.dashboard.LearnerDashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DashboardService {

    public LearnerDashboardDto getLearnerDashboard(String username) {
        return LearnerDashboardDto.builder()
                .activeCoursesCount(0)
                .completedCoursesCount(0)
                .certificatesCount(0)
                .recentActivities(Collections.emptyList())
                .build();
    }

    public InstructorDashboardDto getInstructorDashboard(String username) {
        return InstructorDashboardDto.builder()
                .totalStudents(0)
                .totalCourses(0)
                .averageRating(0.0)
                .build();
    }

    public Object getManagerDashboard() {
        return new Object();
    }

    public Object getAdminDashboard() {
        return new Object();
    }
}

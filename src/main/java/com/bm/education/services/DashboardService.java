package com.bm.education.services;

import com.bm.education.dto.dashboard.InstructorDashboardDto;
import com.bm.education.dto.dashboard.LearnerDashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final com.bm.education.repositories.UserCourseCompletionRepository userCourseCompletionRepository;
    private final com.bm.education.repositories.CourseRepository courseRepository;
    private final com.bm.education.repositories.CertificateRepository certificateRepository;
    private final com.bm.education.repositories.UserRepository userRepository;

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public LearnerDashboardDto getLearnerDashboard(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // This is a simplified implementation.
        // In a real app, we would have specific queries for "active" vs "completed".
        // Assuming UserCourseCompletion tracks completed courses.

        long completedCourses = userCourseCompletionRepository.countByUserId(user.getId()); // Need to add this method
        long certificates = certificateRepository.countByUserId(user.getId()); // Need to add this method

        // Active courses could be calculated as Total Enrolled - Completed.
        // For now, let's assume 0 or implement a proper query later.
        int activeCourses = 0;

        return LearnerDashboardDto.builder()
                .activeCoursesCount(activeCourses)
                .completedCoursesCount((int) completedCourses)
                .certificatesCount((int) certificates)
                .recentActivities(Collections.emptyList()) // Placeholder for activities
                .build();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public InstructorDashboardDto getInstructorDashboard(String username) {
        // Placeholder for instructor stats
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

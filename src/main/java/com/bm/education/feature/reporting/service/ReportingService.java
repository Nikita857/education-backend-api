package com.bm.education.feature.reporting.service;

import com.bm.education.feature.course.repository.CourseRepository;
import com.bm.education.dto.report.UserProgressReport;
import com.bm.education.feature.user.repository.UserRepository;
import com.bm.education.feature.user.repository.UserLessonCompletionRepository;
import com.bm.education.feature.user.repository.UserCourseEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final UserLessonCompletionRepository userLessonCompletionRepository;
    private final com.bm.education.feature.user.repository.UserCourseEnrollmentRepository userCourseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<UserProgressReport> getUserProgressReport(String department, int page, int size) {
        // TODO: Implement new reporting logic based on UserLessonCompletion
        // For now, returning an empty list or basic report
        // This will need a custom query to gather user progress data from new structure
        return java.util.List.of(); // Placeholder - need to implement with proper queries
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Object getCourseStatistics(Long courseId) {
        // Basic stats implementation - count enrollments instead of completions
        long enrolledCount = userCourseEnrollmentRepository.countByCourseId(courseId);
        // For now returning a simple map or DTO. Let's use a Map for flexibility as
        // return type is Object (placeholder style)
        // Ideally should be a DTO.
        return java.util.Map.of(
                "courseId", courseId,
                "enrolledCount", enrolledCount);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Object getDepartmentProgress(String department) {
        // Placeholder - requires complex aggregation
        return java.util.Map.of("department", department, "status", "Not implemented yet");
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Object getCompletionRates() {
        // Placeholder
        return java.util.Map.of("globalCompletionRate", "Not implemented yet");
    }
}
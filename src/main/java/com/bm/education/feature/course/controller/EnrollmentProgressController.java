package com.bm.education.feature.course.controller;

import com.bm.education.shared.type.template.ApiResponse;
import com.bm.education.feature.course.service.EnrollmentProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class EnrollmentProgressController {

    private final EnrollmentProgressService enrollmentProgressService;

    @PostMapping("/lessons/{lessonId}/complete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> completeLesson(@PathVariable Integer lessonId) {
        enrollmentProgressService.markLessonAsCompleted(lessonId);
        return ResponseEntity.ok(ApiResponse.success("Lesson marked as completed"));
    }
}
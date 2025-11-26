package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.course.CourseReviewDto;
import com.bm.education.dto.course.CourseReviewRequest;
import com.bm.education.services.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/reviews")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CourseReviewDto>> createCourseReview(
            @PathVariable Integer courseId,
            @RequestBody CourseReviewRequest courseReviewRequest) {
        CourseReviewDto courseReview = courseReviewService.createCourseReview(
                courseId,
                courseReviewRequest.getRating(),
                courseReviewRequest.getComment()
        );
        return ResponseEntity.ok(ApiResponse.success("Review added successfully", courseReview));
    }
}

package com.bm.education.course.controller;

import com.bm.education.course.model.CourseReview;
import com.bm.education.api.type.template.ApiResponse;
import com.bm.education.dto.course.CourseReviewDto;
import com.bm.education.dto.course.CourseReviewRequest;
import com.bm.education.course.service.CourseReviewService;
import lombok.RequiredArgsConstructor;
import com.bm.education.course.mapper.CourseReviewMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/reviews")
@RequiredArgsConstructor
public class CourseReviewController {

        private final CourseReviewService courseReviewService;
        private final CourseReviewMapper courseReviewMapper;

        @PostMapping
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<ApiResponse<CourseReviewDto>> createCourseReview(
                        @PathVariable Integer courseId,
                        @RequestBody CourseReviewRequest courseReviewRequest) {
                String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                                .getAuthentication().getName();
                CourseReview courseReview = courseReviewService.createCourseReview(
                                courseId,
                                courseReviewRequest.getRating(),
                                courseReviewRequest.getComment(),
                                username);
                return ResponseEntity
                                .ok(ApiResponse.success("Review added successfully",
                                                courseReviewMapper.toDto(courseReview)));
        }
}

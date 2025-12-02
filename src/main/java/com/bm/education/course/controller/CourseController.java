package com.bm.education.course.controller;

import com.bm.education.api.type.template.ApiResponse;
import com.bm.education.api.type.page.PageResponse;
import com.bm.education.dto.course.CourseDto;
import com.bm.education.course.model.CourseStatus;
import com.bm.education.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseDto>>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CourseStatus status) {

        PageResponse<CourseDto> response = courseService.getAllCourses(page, size, sortBy, sortDir, title, category,
                status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{courseSlug}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable String courseSlug) {
        CourseDto courseDto = courseService.getCourseBySlug(courseSlug);
        return ResponseEntity.ok(ApiResponse.success(courseDto));
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody CourseDto courseDto) {
        CourseDto savedCourseDto = courseService.createCourse(courseDto);
        return ResponseEntity.ok(ApiResponse.success("Course created successfully", savedCourseDto));
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(@PathVariable Integer courseId,
            @RequestBody CourseDto courseDto) {
        CourseDto updatedCourseDto = courseService.updateCourse(courseId, courseDto);
        return ResponseEntity.ok(ApiResponse.success("Course updated successfully", updatedCourseDto));
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Course deleted successfully"));
    }

    @PostMapping("/{courseId}/enroll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> enrollInCourse(@PathVariable Integer courseId) {
        courseService.enroll(courseId);
        return ResponseEntity.ok(ApiResponse.success("Enrolled in course successfully"));
    }
}
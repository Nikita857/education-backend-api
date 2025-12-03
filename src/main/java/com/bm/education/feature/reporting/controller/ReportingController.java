package com.bm.education.feature.reporting.controller;

import com.bm.education.shared.type.template.ApiResponse;
import com.bm.education.dto.report.UserProgressReport;
import com.bm.education.feature.reporting.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping("/user-progress")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ApiResponse<List<UserProgressReport>>> getUserProgressReport(
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<UserProgressReport> reports = reportingService.getUserProgressReport(department, page, size);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("/course-statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ApiResponse<Object>> getCourseStatistics(@RequestParam Integer courseId) {
        Object stats = reportingService.getCourseStatistics(courseId);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/department-progress")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ApiResponse<Object>> getDepartmentProgress(@RequestParam String department) {
        Object progress = reportingService.getDepartmentProgress(department);
        return ResponseEntity.ok(ApiResponse.success(progress));
    }

    @GetMapping("/completion-rates")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ApiResponse<Object>> getCompletionRates() {
        Object rates = reportingService.getCompletionRates();
        return ResponseEntity.ok(ApiResponse.success(rates));
    }
}
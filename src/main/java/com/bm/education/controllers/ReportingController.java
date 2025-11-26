package com.bm.education.controllers;

import com.bm.education.dto.report.UserProgressReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportingController {

    @GetMapping("/user-progress")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<UserProgressReport>> getUserProgressReport(
            @RequestParam(required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Implementation to get user progress reports
        return ResponseEntity.ok(List.of()); // Placeholder
    }

    @GetMapping("/course-statistics")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<Object> getCourseStatistics(@RequestParam Integer courseId) {
        // Implementation to get course statistics
        return ResponseEntity.ok(new Object()); // Placeholder
    }

    @GetMapping("/department-progress")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<Object> getDepartmentProgress(@RequestParam String department) {
        // Implementation to get progress for a department
        return ResponseEntity.ok(new Object()); // Placeholder
    }

    @GetMapping("/completion-rates")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<Object> getCompletionRates() {
        // Implementation to get overall completion rates
        return ResponseEntity.ok(new Object()); // Placeholder
    }
}
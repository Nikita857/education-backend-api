package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.dashboard.InstructorDashboardDto;
import com.bm.education.dto.dashboard.LearnerDashboardDto;
import com.bm.education.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/learner")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<LearnerDashboardDto>> getLearnerDashboard() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        LearnerDashboardDto dashboard = dashboardService.getLearnerDashboard(username);
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }

    @GetMapping("/instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<InstructorDashboardDto>> getInstructorDashboard() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        InstructorDashboardDto dashboard = dashboardService.getInstructorDashboard(username);
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<Object>> getManagerDashboard() {
        Object dashboard = dashboardService.getManagerDashboard();
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getAdminDashboard() {
        Object dashboard = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }
}
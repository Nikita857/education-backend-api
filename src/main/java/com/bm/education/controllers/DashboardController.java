package com.bm.education.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @GetMapping("/learner")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getLearnerDashboard() {
        // Implementation for learner dashboard
        return ResponseEntity.ok(new Object()); // Placeholder
    }

    @GetMapping("/instructor")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Object> getInstructorDashboard() {
        // Implementation for instructor dashboard
        return ResponseEntity.ok(new Object()); // Placeholder
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> getManagerDashboard() {
        // Implementation for manager dashboard
        return ResponseEntity.ok(new Object()); // Placeholder
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAdminDashboard() {
        // Implementation for admin dashboard
        return ResponseEntity.ok(new Object()); // Placeholder
    }
}
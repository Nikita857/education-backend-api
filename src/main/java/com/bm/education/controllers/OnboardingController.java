package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.onboarding.AdaptationProgramDto;
import com.bm.education.mapper.OnboardingMapper;
import com.bm.education.models.AdaptationProgram;
import com.bm.education.services.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final OnboardingMapper onboardingMapper;

    @GetMapping("/programs")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdaptationProgramDto>>> getOnboardingPrograms() {
        List<AdaptationProgram> programs = onboardingService.getAllPrograms();
        List<AdaptationProgramDto> programDtos = programs.stream()
                .map(onboardingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(programDtos));
    }

    @PostMapping("/programs")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdaptationProgramDto>> createOnboardingProgram(
            @RequestBody AdaptationProgramDto programDto) {
        AdaptationProgram program = onboardingMapper.toEntity(programDto);
        AdaptationProgram savedProgram = onboardingService.createProgram(program);
        return ResponseEntity.ok(ApiResponse.success(onboardingMapper.toDto(savedProgram)));
    }

    @PutMapping("/programs/{programId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdaptationProgramDto>> updateOnboardingProgram(
            @PathVariable Long programId,
            @RequestBody AdaptationProgramDto programDto) {
        AdaptationProgram program = onboardingMapper.toEntity(programDto);
        AdaptationProgram updatedProgram = onboardingService.updateProgram(programId, program);
        return ResponseEntity.ok(ApiResponse.success(onboardingMapper.toDto(updatedProgram)));
    }

    @GetMapping("/assignments")
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdaptationProgramDto>>> getOnboardingAssignments() {
        // Implementation to get onboarding assignments
        return ResponseEntity.ok(ApiResponse.success(List.of())); // Placeholder
    }

    @PostMapping("/assignments")
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdaptationProgramDto>> assignOnboardingProgram(
            @RequestBody AdaptationProgramDto assignmentDto) {
        // Implementation to assign an onboarding program to an employee
        return ResponseEntity.ok(ApiResponse.success(new AdaptationProgramDto())); // Placeholder
    }

    @GetMapping("/progress/{assignmentId}")
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getOnboardingProgress(@PathVariable Long assignmentId) {
        // Implementation to get onboarding progress
        return ResponseEntity.ok(ApiResponse.success(new Object())); // Placeholder
    }
}
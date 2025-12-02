package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.course.LessonDto;
import com.bm.education.dto.course.ModuleDto;
import com.bm.education.services.LessonService;
import com.bm.education.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ModuleLessonController {

    private final ModuleService moduleService;
    private final LessonService lessonService;

    // Module endpoints
    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<ApiResponse<List<ModuleDto>>> getCourseModules(@PathVariable Integer courseId) {
        return ResponseEntity.ok(ApiResponse.success(moduleService.getCourseModules(courseId)));
    }

    @PostMapping("/courses/{courseId}/modules")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ModuleDto>> createModule(@PathVariable Integer courseId, @RequestBody ModuleDto moduleDto) {
        ModuleDto savedModule = moduleService.createModule(courseId, moduleDto);
        return ResponseEntity.ok(ApiResponse.success("Module created successfully", savedModule));
    }

    @PutMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ModuleDto>> updateModule(@PathVariable Integer moduleId, @RequestBody ModuleDto moduleDto) {
        ModuleDto updatedModule = moduleService.updateModule(moduleId, moduleDto);
        return ResponseEntity.ok(ApiResponse.success("Module updated successfully", updatedModule));
    }

    @DeleteMapping("/modules/{moduleId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable Integer moduleId) {
        moduleService.deleteModule(moduleId);
        return ResponseEntity.ok(ApiResponse.success("Module deleted successfully"));
    }

    // Lesson endpoints
    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<ApiResponse<List<LessonDto>>> getModuleLessons(@PathVariable Integer moduleId) {
        return ResponseEntity.ok(ApiResponse.success(lessonService.getModuleLessons(moduleId)));
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonDto>> createLesson(@PathVariable Integer moduleId, @RequestBody LessonDto lessonDto) {
        LessonDto savedLesson = lessonService.createLesson(moduleId, lessonDto);
        return ResponseEntity.ok(ApiResponse.success("Lesson created successfully", savedLesson));
    }

    @PutMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonDto>> updateLesson(@PathVariable Integer lessonId, @RequestBody LessonDto lessonDto) {
        LessonDto updatedLesson = lessonService.updateLesson(lessonId, lessonDto);
        return ResponseEntity.ok(ApiResponse.success("Lesson updated successfully", updatedLesson));
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable Integer lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.ok(ApiResponse.success("Lesson deleted successfully"));
    }
}
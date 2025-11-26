package com.bm.education.mapper;

import com.bm.education.dto.progress.UserProgressDto;
import com.bm.education.models.UserProgress;
import org.springframework.stereotype.Component;

@Component
public class UserProgressMapper {

    public UserProgressDto toDto(UserProgress progress) {
        UserProgressDto dto = new UserProgressDto();
        dto.setId(progress.getId());
        dto.setUserId(progress.getUser().getId());
        if (progress.getCourse() != null) {
            dto.setCourseId(progress.getCourse().getId());
        }
        if (progress.getModule() != null) {
            dto.setModuleId(progress.getModule().getId());
        }
        if (progress.getLesson() != null) {
            dto.setLessonId(progress.getLesson().getId());
        }
        dto.setCompletedAt(progress.getCompletedAt());
        return dto;
    }

    public UserProgress toEntity(UserProgressDto dto) {
        UserProgress progress = new UserProgress();
        progress.setId(dto.getId());
        progress.setCompletedAt(dto.getCompletedAt());
        return progress;
    }
}
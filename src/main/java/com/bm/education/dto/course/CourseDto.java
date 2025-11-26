package com.bm.education.dto.course;

import com.bm.education.models.CourseDifficultyLevel;
import com.bm.education.models.CourseFormat;
import com.bm.education.models.CourseStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseDto {
    private Integer id;
    private String title;
    private String image;
    private String description;
    private String slug;
    private Integer durationHours;
    private CourseStatus status;
    private CourseDifficultyLevel difficultyLevel;
    private CourseFormat format;
    private String scormPackagePath;
    private Double averageRating;
    private Integer totalReviews;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;  // ID категории
    private List<Long> tagIds; // Список ID тегов
    private List<ModuleDto> modules; // Список модулей
}
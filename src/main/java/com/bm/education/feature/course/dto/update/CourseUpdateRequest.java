package com.bm.education.feature.course.dto.update;

import java.util.Set;

import com.bm.education.feature.course.model.CourseDifficultyLevel;
import com.bm.education.feature.course.model.CourseFormat;
import com.bm.education.feature.course.model.CourseStatus;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CourseUpdateRequest(

    @Size(max = 100)
    String title,

    @Size(max = 255)
    String image,

    String description,

    @Pattern(regexp = "^[a-z0-9-]+$", message = "Только латиница, цифры и дефис")
    @Size(max = 100)
    String slug,

    Integer categoryId,

    Set<String> tagNames,

    CourseStatus status,

    CourseDifficultyLevel difficultyLevel,

    CourseFormat format,

    String scormPackageUrl,

    @PositiveOrZero
    Integer durationMinutes
) {}

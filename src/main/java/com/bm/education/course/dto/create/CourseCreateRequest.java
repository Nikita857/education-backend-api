package com.bm.education.course.dto.create;

import jakarta.validation.constraints.*;
import java.util.Set;

import com.bm.education.course.model.CourseDifficultyLevel;
import com.bm.education.course.model.CourseFormat;
import com.bm.education.course.model.CourseStatus;

public record CourseCreateRequest(

    @NotBlank @Size(max = 100)
    String title,

    @Size(max = 255)
    String image,

    String description,

    @NotBlank
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Только латиница, цифры и дефис")
    @Size(max = 100)
    String slug,

    @Positive
    Integer categoryId,

    Set<String> tagNames,                     

    @NotNull
    CourseStatus status,

    CourseDifficultyLevel difficultyLevel,

    CourseFormat format,

    String scormPackageUrl,                   

    @PositiveOrZero
    Integer durationMinutes                   
) {}

package com.bm.education.feature.course.dto.read;

import java.time.Instant;
import java.util.Set;

import com.bm.education.feature.course.model.CourseDifficultyLevel;
import com.bm.education.feature.course.model.CourseFormat;
import com.bm.education.feature.course.model.CourseStatus;

public record CourseResponse(

    Long id,
    String title,
    String image,
    String description,
    String slug,

    String categoryName,                      // не ID, а название — удобнее фронту
    Set<String> tags,

    CourseStatus status,
    CourseDifficultyLevel difficultyLevel,
    CourseFormat format,

    String scormPackageUrl,
    Integer durationMinutes,

    Double averageRating,                     // считаем на лету
    Long totalReviews,

    Instant createdAt,
    Instant updatedAt
) {}

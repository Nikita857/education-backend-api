package com.bm.education.dto.course;

import com.bm.education.models.LessonContentType;
import lombok.Data;

@Data
public class LessonDto {
    private Integer id;
    private String title;
    private String description;
    private String textContent;
    private LessonContentType contentType;
    private String video;
    private String scormPackagePath;
    private String webinarUrl;
    private java.time.Instant webinarStartTime;
    private Integer webinarDurationMinutes;
    private String filePath;
    private String contentUrl;
    private Integer estimatedDurationMinutes;
    private Boolean viewingProgressRequired;
    private Integer viewingPercentageThreshold;
}
package com.bm.education.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CourseReviewDto {
    private Long id;
    private Integer userId;
    private String userFirstName;
    private Integer courseId;
    private Integer rating;
    private String comment;
    private Instant createdAt;

    public CourseReviewDto(Long id, Integer userId, String userFirstName, Integer courseId, Integer rating, String comment, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}

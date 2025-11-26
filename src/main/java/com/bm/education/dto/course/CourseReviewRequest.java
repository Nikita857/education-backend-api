package com.bm.education.dto.course;

import lombok.Data;

@Data
public class CourseReviewRequest {
    private Integer rating;
    private String comment;
}

package com.bm.education.dto.quiz;

import com.bm.education.models.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuizRequest {
    private String title;
    private String description;
    private Integer passingScore;
    private Integer timeLimitMinutes;
    private Integer maxAttempts;
    private Boolean randomizeQuestions;
    private Boolean showCorrectAnswers;
    private Long lessonId;
    private List<QuestionRequest> questions;
}
package com.bm.education.feature.quiz.dto;

import com.bm.education.feature.quiz.model.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private QuestionType questionType;
    private Integer points;
    private List<AnswerOptionRequest> answerOptions;
}

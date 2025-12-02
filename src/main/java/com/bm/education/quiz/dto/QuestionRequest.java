package com.bm.education.quiz.dto;

import com.bm.education.quiz.model.QuestionType;
import com.bm.education.quiz.dto.AnswerOptionRequest;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private QuestionType questionType;
    private Integer points;
    private List<AnswerOptionRequest> answerOptions;
}

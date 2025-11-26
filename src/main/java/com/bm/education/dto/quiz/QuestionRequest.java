package com.bm.education.dto.quiz;

import com.bm.education.models.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private QuestionType questionType;
    private Integer points;
    private List<AnswerOptionRequest> answerOptions;
}

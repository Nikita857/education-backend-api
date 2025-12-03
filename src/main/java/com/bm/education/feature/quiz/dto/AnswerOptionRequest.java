package com.bm.education.feature.quiz.dto;

import lombok.Data;

@Data
public class AnswerOptionRequest {
    private String optionText;
    private boolean isCorrect;
}

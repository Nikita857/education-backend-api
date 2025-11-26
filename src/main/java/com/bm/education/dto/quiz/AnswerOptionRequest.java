package com.bm.education.dto.quiz;

import lombok.Data;

@Data
public class AnswerOptionRequest {
    private String optionText;
    private boolean isCorrect;
}

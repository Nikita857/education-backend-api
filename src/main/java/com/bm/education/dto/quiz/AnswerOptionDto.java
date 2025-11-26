package com.bm.education.dto.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerOptionDto {
    private Long id;
    private String optionText;

    // Constructor for DTO projection
    public AnswerOptionDto(Long id, String optionText) {
        this.id = id;
        this.optionText = optionText;
    }
}

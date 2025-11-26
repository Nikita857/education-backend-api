package com.bm.education.dto.quiz;

import com.bm.education.models.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionText;
    private QuestionType questionType;
    private Integer points;
    private List<AnswerOptionDto> answerOptions;

    // Constructor for DTO projection or manual mapping
    public QuestionDto(Long id, String questionText, QuestionType questionType, Integer points) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.points = points;
    }
}

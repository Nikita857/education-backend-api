package com.bm.education.quiz.dto;

import com.bm.education.quiz.dto.QuestionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDto> questions;

    // Constructor for DTO projection or manual mapping
    public QuizDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}

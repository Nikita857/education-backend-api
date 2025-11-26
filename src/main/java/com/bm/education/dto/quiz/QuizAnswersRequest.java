package com.bm.education.dto.quiz;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class QuizAnswersRequest {
    private Map<Long, List<Long>> selectedAnswers; // questionId -> list of answerOptionIds
    private Map<Long, String> textAnswers;       // questionId -> text answer
}

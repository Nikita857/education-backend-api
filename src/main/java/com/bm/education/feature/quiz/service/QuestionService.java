package com.bm.education.feature.quiz.service;

import com.bm.education.dto.quiz.QuestionRequest;
import com.bm.education.feature.quiz.model.AnswerOption;
import com.bm.education.feature.quiz.model.Question;
import com.bm.education.feature.quiz.model.Test;
import com.bm.education.feature.quiz.repository.QuestionRepository;
import com.bm.education.feature.quiz.repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    @Transactional
    public Question createQuestion(Long testId, QuestionRequest questionRequest) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("Test not found with id: " + testId));

        Question question = Question.builder()
                .test(test)
                .questionText(questionRequest.getQuestionText())
                .questionType(questionRequest.getQuestionType())
                .points(questionRequest.getPoints())
                .build();

        Set<AnswerOption> answerOptions = new HashSet<>();
        if (questionRequest.getAnswerOptions() != null) {
            for (var optionRequest : questionRequest.getAnswerOptions()) {
                AnswerOption answerOption = AnswerOption.builder()
                        .question(question)
                        .optionText(optionRequest.getOptionText())
                        .isCorrect(optionRequest.isCorrect())
                        .build();
                answerOptions.add(answerOption);
            }
        }
        question.setAnswerOptions(answerOptions);

        return questionRepository.save(question);
    }
}

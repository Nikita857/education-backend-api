package com.bm.education.services;

import com.bm.education.dto.quiz.QuestionDto;
import com.bm.education.dto.quiz.QuestionRequest;
import com.bm.education.mapper.QuizMapper;
import com.bm.education.models.AnswerOption;
import com.bm.education.models.Question;
import com.bm.education.models.Test;
import com.bm.education.repositories.QuestionRepository;
import com.bm.education.repositories.TestRepository;
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
    private final QuizMapper quizMapper;

    @Transactional
    public QuestionDto createQuestion(Long testId, QuestionRequest questionRequest) {
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

        Question savedQuestion = questionRepository.save(question);
        return quizMapper.toQuestionDto(savedQuestion);
    }
}

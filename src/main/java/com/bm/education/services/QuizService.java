package com.bm.education.services;

import com.bm.education.dto.quiz.QuizDto;
import com.bm.education.dto.quiz.QuizRequest;
import com.bm.education.mapper.QuizMapper;
import com.bm.education.models.Lesson;
import com.bm.education.models.Test;
import com.bm.education.repositories.LessonRepository;
import com.bm.education.repositories.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;
    private final QuizMapper quizMapper;

    @Transactional(readOnly = true)
    public QuizDto getQuizForStudent(Long quizId) {
        Test test = testRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found: " + quizId));
        return quizMapper.toQuizDto(test);
    }

    @Transactional
    public QuizDto createQuiz(QuizRequest quizRequest) {
        Lesson lesson = null;
        if (quizRequest.getLessonId() != null) {
            lesson = lessonRepository.findById(quizRequest.getLessonId().intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        }

        Test quiz = Test.builder()
                .title(quizRequest.getTitle())
                .description(quizRequest.getDescription())
                .passingScore(quizRequest.getPassingScore())
                .timeLimitMinutes(quizRequest.getTimeLimitMinutes())
                .maxAttempts(quizRequest.getMaxAttempts())
                .randomizeQuestions(quizRequest.getRandomizeQuestions())
                .showCorrectAnswers(quizRequest.getShowCorrectAnswers())
                .lesson(lesson)
                .build();

        Test savedQuiz = testRepository.save(quiz);
        return quizMapper.toQuizDto(savedQuiz);
    }

    @Transactional
    public QuizDto updateQuiz(Long quizId, QuizRequest quizRequest) {
        Test existingQuiz = testRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found: " + quizId));

        existingQuiz.setTitle(quizRequest.getTitle());
        existingQuiz.setDescription(quizRequest.getDescription());
        existingQuiz.setPassingScore(quizRequest.getPassingScore());
        existingQuiz.setTimeLimitMinutes(quizRequest.getTimeLimitMinutes());
        existingQuiz.setMaxAttempts(quizRequest.getMaxAttempts());
        existingQuiz.setRandomizeQuestions(quizRequest.getRandomizeQuestions());
        existingQuiz.setShowCorrectAnswers(quizRequest.getShowCorrectAnswers());

        if (quizRequest.getLessonId() != null) {
            Lesson lesson = lessonRepository.findById(quizRequest.getLessonId().intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
            existingQuiz.setLesson(lesson);
        } else {
            existingQuiz.setLesson(null);
        }

        Test updatedQuiz = testRepository.save(existingQuiz);
        return quizMapper.toQuizDto(updatedQuiz);
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!testRepository.existsById(quizId)) {
            throw new EntityNotFoundException("Quiz not found: " + quizId);
        }
        testRepository.deleteById(quizId);
    }
}

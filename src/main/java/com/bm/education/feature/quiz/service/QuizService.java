package com.bm.education.feature.quiz.service;

import com.bm.education.dto.quiz.QuizRequest;
import com.bm.education.feature.lesson.model.Lesson;
import com.bm.education.feature.quiz.model.Test;
import com.bm.education.feature.lesson.repository.LessonRepository;
import com.bm.education.feature.quiz.repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final TestRepository testRepository;
    private final LessonRepository lessonRepository;

    @Transactional(readOnly = true)
    public Test getQuizForStudent(Long quizId) {
        return testRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found: " + quizId));
    }

    @Transactional
    public Test createQuiz(QuizRequest quizRequest) {
        Lesson lesson = null;
        if (quizRequest.getLessonId() != null) {
            lesson = lessonRepository.findById(quizRequest.getLessonId())
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

        return testRepository.save(quiz);
    }

    @Transactional
    public Test updateQuiz(Long quizId, QuizRequest quizRequest) {
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
            Lesson lesson = lessonRepository.findById(quizRequest.getLessonId())
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
            existingQuiz.setLesson(lesson);
        } else {
            existingQuiz.setLesson(null);
        }

        return testRepository.save(existingQuiz);
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!testRepository.existsById(quizId)) {
            throw new EntityNotFoundException("Quiz not found: " + quizId);
        }
        testRepository.deleteById(quizId);
    }
}

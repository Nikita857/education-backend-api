package com.bm.education.controllers;

import com.bm.education.dto.common.ApiResponse;
import com.bm.education.dto.quiz.*;
import com.bm.education.mapper.QuizMapper;
import com.bm.education.services.QuestionService;
import com.bm.education.services.QuizAttemptService;
import com.bm.education.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizAttemptService quizAttemptService;
    private final QuizMapper quizMapper;

    @GetMapping("/{quizId}")
    public ResponseEntity<ApiResponse<QuizDto>> getQuiz(@PathVariable Long quizId) {
        com.bm.education.models.Test quiz = quizService.getQuizForStudent(quizId);
        return ResponseEntity.ok(ApiResponse.success(quizMapper.toQuizDto(quiz)));
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(@RequestBody QuizRequest quizRequest) {
        com.bm.education.models.Test savedQuiz = quizService.createQuiz(quizRequest);
        return ResponseEntity.ok(ApiResponse.success("Quiz created successfully", quizMapper.toQuizDto(savedQuiz)));
    }

    @PutMapping("/{quizId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuizDto>> updateQuiz(@PathVariable Long quizId,
            @RequestBody QuizRequest quizRequest) {
        com.bm.education.models.Test updatedQuiz = quizService.updateQuiz(quizId, quizRequest);
        return ResponseEntity.ok(ApiResponse.success("Quiz updated successfully", quizMapper.toQuizDto(updatedQuiz)));
    }

    @DeleteMapping("/{quizId}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok(ApiResponse.success("Quiz deleted successfully"));
    }

    private final com.bm.education.mapper.TestResultMapper testResultMapper;

    @PostMapping("/{quizId}/questions")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuestionDto>> addQuestionToQuiz(@PathVariable Long quizId,
            @RequestBody QuestionRequest questionRequest) {
        com.bm.education.models.Question savedQuestion = questionService.createQuestion(quizId, questionRequest);
        return ResponseEntity
                .ok(ApiResponse.success("Question added successfully", quizMapper.toQuestionDto(savedQuestion)));
    }

    // Quiz attempt endpoints
    @PostMapping("/{quizId}/attempt")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TestResultDto>> startQuizAttempt(@PathVariable Long quizId) {
        com.bm.education.models.TestResult testResult = quizAttemptService.startQuizAttempt(quizId);
        return ResponseEntity.ok(ApiResponse.success("Quiz attempt started", testResultMapper.toDto(testResult)));
    }

    @PutMapping("/attempts/{attemptId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> saveQuizAnswers(@PathVariable Long attemptId,
            @RequestBody QuizAnswersRequest answersRequest) {
        quizAttemptService.saveQuizAnswers(attemptId, answersRequest);
        return ResponseEntity.ok(ApiResponse.success("Answers saved successfully"));
    }

    @PostMapping("/attempts/{attemptId}/submit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TestResultDto>> submitQuizAttempt(@PathVariable Long attemptId) {
        com.bm.education.models.TestResult testResult = quizAttemptService.submitQuizAttempt(attemptId);
        return ResponseEntity
                .ok(ApiResponse.success("Quiz submitted successfully", testResultMapper.toDto(testResult)));
    }

    @GetMapping("/attempts/{attemptId}/result")
    public ResponseEntity<ApiResponse<TestResultDto>> getQuizResult(@PathVariable Long attemptId) {
        com.bm.education.models.TestResult result = quizAttemptService.getTestResult(attemptId);
        return ResponseEntity.ok(ApiResponse.success(testResultMapper.toDto(result)));
    }
}
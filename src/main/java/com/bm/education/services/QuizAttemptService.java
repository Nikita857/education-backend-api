package com.bm.education.services;

import com.bm.education.dto.quiz.QuizAnswersRequest;
import com.bm.education.dto.quiz.TestResultDto;
import com.bm.education.exceptions.TestLimitExceededException;
import com.bm.education.mapper.TestResultMapper;
import com.bm.education.models.*;
import com.bm.education.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizAttemptService {

    private final TestResultRepository testResultRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final EnrollmentProgressService enrollmentProgressService;
    private final TestResultMapper testResultMapper;

    @Transactional
    public TestResultDto startQuizAttempt(Long testId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("Test not found"));

        // Check if user has exceeded max attempts
        long attempts = testResultRepository.countByTestAndUser(test, user);
        if (test.getMaxAttempts() != null && attempts >= test.getMaxAttempts()) {
            throw new TestLimitExceededException("You have exceeded the maximum number of attempts for this quiz.");
        }

        TestResult testResult = new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setPassed(false);
        TestResult savedResult = testResultRepository.save(testResult);

        return testResultMapper.toDto(savedResult);
    }

    @Transactional
    public void saveQuizAnswers(Long attemptId, QuizAnswersRequest answersRequest) {
        TestResult testResult = testResultRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Quiz attempt not found"));

        userAnswerRepository.deleteByTestResult(testResult);

        List<UserAnswer> userAnswers = new ArrayList<>();

        if (answersRequest.getSelectedAnswers() != null) {
            answersRequest.getSelectedAnswers().forEach((questionId, answerOptionIds) -> {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Question not found"));
                answerOptionIds.forEach(answerOptionId -> {
                    AnswerOption answerOption = answerOptionRepository.findById(answerOptionId)
                            .orElseThrow(() -> new EntityNotFoundException("Answer option not found"));
                    UserAnswer userAnswer = new UserAnswer();
                    userAnswer.setTestResult(testResult);
                    userAnswer.setQuestion(question);
                    userAnswer.setChosenAnswer(answerOption);
                    userAnswers.add(userAnswer);
                });
            });
        }

        if (answersRequest.getTextAnswers() != null) {
            answersRequest.getTextAnswers().forEach((questionId, textAnswer) -> {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Question not found"));
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setTestResult(testResult);
                userAnswer.setQuestion(question);
                userAnswer.setTextAnswer(textAnswer);
                userAnswers.add(userAnswer);
            });
        }

        userAnswerRepository.saveAll(userAnswers);
    }

    @Transactional
    public TestResultDto submitQuizAttempt(Long attemptId) {
        TestResult testResult = testResultRepository.findById(attemptId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz attempt not found"));

        if (testResult.getCompletedAt() != null) {
            throw new RuntimeException("This quiz attempt has already been submitted.");
        }

        int totalScore = 0;
        int maxPossibleScore = 0;
        List<UserAnswer> userAnswers = userAnswerRepository.findByTestResult(testResult);

        var answersByQuestion = userAnswers.stream()
                .collect(Collectors.groupingBy(UserAnswer::getQuestion));

        for (Question question : testResult.getTest().getQuestions()) {
            maxPossibleScore += question.getPoints();
            List<UserAnswer> answersForQuestion = answersByQuestion.getOrDefault(question, new ArrayList<>());

            if (!answersForQuestion.isEmpty()) {
                boolean isCorrect = isAnswerCorrect(question, answersForQuestion);
                if (isCorrect) {
                    totalScore += question.getPoints();
                }
            }
        }

        testResult.setScore(totalScore);
        testResult.setMaxScore(maxPossibleScore);
        double percentage = (maxPossibleScore > 0) ? ((double) totalScore / maxPossibleScore) * 100 : 0;
        testResult.setPercentage(percentage);
        testResult.setCompletedAt(Instant.now());
        testResult.setPassed(percentage >= testResult.getTest().getPassingScore());

        TestResult savedTestResult = testResultRepository.save(testResult);

        if (savedTestResult.getPassed() && testResult.getTest().getLesson() != null) {
            enrollmentProgressService.markLessonAsCompleted(testResult.getTest().getLesson().getId());
        }

        return testResultMapper.toDto(savedTestResult);
    }

    public TestResultDto getTestResult(Long attemptId) {
        TestResult result = testResultRepository.findById(attemptId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz result not found: " + attemptId));
        return testResultMapper.toDto(result);
    }

    private boolean isAnswerCorrect(Question question, List<UserAnswer> userAnswers) {
        if (question.getQuestionType() == QuestionType.SINGLE_CHOICE) {
            if (userAnswers.size() != 1) return false;
            return userAnswers.getFirst().getChosenAnswer() != null && userAnswers.getFirst().getChosenAnswer().getIsCorrect();
        } else if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            List<AnswerOption> correctOptions = question.getAnswerOptions().stream()
                    .filter(AnswerOption::getIsCorrect)
                    .toList();

            List<AnswerOption> chosenOptions = userAnswers.stream()
                    .map(UserAnswer::getChosenAnswer)
                    .filter(java.util.Objects::nonNull)
                    .toList();

            return correctOptions.size() == chosenOptions.size() &&
                    new HashSet<>(chosenOptions).containsAll(correctOptions);
        } else if (question.getQuestionType() == QuestionType.TEXT_RESPONSE) {
            return false;
        }
        return false;
    }
}

package com.bm.education.quiz.repository;

import com.bm.education.quiz.model.TestResult;
import com.bm.education.quiz.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByTestResult(TestResult testResult);
    void deleteByTestResult(TestResult testResult);
}

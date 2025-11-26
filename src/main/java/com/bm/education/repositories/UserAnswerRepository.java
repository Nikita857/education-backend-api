package com.bm.education.repositories;

import com.bm.education.models.TestResult;
import com.bm.education.models.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByTestResult(TestResult testResult);
    void deleteByTestResult(TestResult testResult);
}

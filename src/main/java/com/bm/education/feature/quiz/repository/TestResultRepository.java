package com.bm.education.feature.quiz.repository;

import com.bm.education.feature.quiz.model.Test;
import com.bm.education.feature.quiz.model.TestResult;
import com.bm.education.feature.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    long countByTestAndUser(Test test, User user);
}
package com.bm.education.quiz.repository;

import com.bm.education.quiz.model.Test;
import com.bm.education.quiz.model.TestResult;
import com.bm.education.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    long countByTestAndUser(Test test, User user);
}
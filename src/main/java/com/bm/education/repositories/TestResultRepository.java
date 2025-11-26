package com.bm.education.repositories;

import com.bm.education.models.Test;
import com.bm.education.models.TestResult;
import com.bm.education.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    long countByTestAndUser(Test test, User user);
}
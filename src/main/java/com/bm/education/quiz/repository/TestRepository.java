package com.bm.education.quiz.repository;

import com.bm.education.quiz.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByLessonId(Integer lessonId);
    Optional<Test> findByTitle(String title);
}

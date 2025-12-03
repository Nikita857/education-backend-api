package com.bm.education.feature.quiz.repository;

import com.bm.education.feature.quiz.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByLessonId(Long lessonId);
    Optional<Test> findByTitle(String title);
}

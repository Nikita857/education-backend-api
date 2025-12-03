package com.bm.education.feature.lesson.repository;

import com.bm.education.feature.lesson.model.LessonTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonTestRepository extends JpaRepository<LessonTest, Long> {
}
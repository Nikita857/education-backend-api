package com.bm.education.repositories;

import com.bm.education.models.Course;
import com.bm.education.models.Lesson;
import com.bm.education.models.User;
import com.bm.education.models.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Integer> {
    boolean existsByUserAndLesson(User user, Lesson lesson);
    long countByUserAndLesson_Module_Course(User user, Course course);
}
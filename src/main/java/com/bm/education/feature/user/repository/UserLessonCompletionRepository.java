package com.bm.education.feature.user.repository;

import com.bm.education.feature.user.model.User;
import com.bm.education.feature.user.model.UserLessonCompletion;
import com.bm.education.feature.lesson.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLessonCompletionRepository extends JpaRepository<UserLessonCompletion, Long> {
    boolean existsByUserAndLesson(User user, Lesson lesson);

    Optional<UserLessonCompletion> findByUserAndLesson(User user, Lesson lesson);

    @Query("SELECT COUNT(ulc) FROM UserLessonCompletion ulc WHERE ulc.user = :user AND ulc.lesson.module.course = :course")
    long countByUserAndLessonModuleCourse(@Param("user") User user, @Param("course") com.bm.education.feature.course.model.Course course);
}
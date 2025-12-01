package com.bm.education.repositories;

import com.bm.education.models.Course;
import com.bm.education.models.User;
import com.bm.education.models.UserCourseCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCourseCompletionRepository extends JpaRepository<UserCourseCompletion, Long> {
    Optional<UserCourseCompletion> findByUserAndCourse(User user, Course course);

    long countByCourseId(Integer courseId);

    long countByUserId(Integer userId);
}
package com.bm.education.feature.user.repository;

import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.user.model.User;
import com.bm.education.feature.user.model.UserCourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourseEnrollment, Long> {
    boolean existsByUserAndCourse(User user, Course course);

    Optional<UserCourseEnrollment> findByUserAndCourse(User user, Course course);

    List<UserCourseEnrollment> findByUser(User user);
}
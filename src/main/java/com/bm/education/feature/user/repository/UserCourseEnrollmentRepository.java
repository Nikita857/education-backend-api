package com.bm.education.feature.user.repository;

import com.bm.education.feature.user.model.User;
import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.user.model.UserCourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCourseEnrollmentRepository extends JpaRepository<UserCourseEnrollment, Long> {
    Optional<UserCourseEnrollment> findByUserAndCourse(User user, Course course);
    boolean existsByUserAndCourse(User user, Course course);
    long countByCourseId(Long courseId);
    long countByUserIdAndStatus(Long userId, UserCourseEnrollment.EnrollmentStatus status);
}
package com.bm.education.course.repository;

import com.bm.education.course.model.Course;
import com.bm.education.course.model.CourseReview;
import com.bm.education.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    boolean existsByUserAndCourse(User user, Course course);
}
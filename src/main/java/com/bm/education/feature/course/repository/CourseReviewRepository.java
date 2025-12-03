package com.bm.education.feature.course.repository;

import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.course.model.CourseReview;
import com.bm.education.feature.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    boolean existsByUserAndCourse(User user, Course course);
}
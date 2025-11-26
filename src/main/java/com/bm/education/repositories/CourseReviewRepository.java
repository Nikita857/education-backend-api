package com.bm.education.repositories;

import com.bm.education.models.Course;
import com.bm.education.models.CourseReview;
import com.bm.education.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    boolean existsByUserAndCourse(User user, Course course);
}
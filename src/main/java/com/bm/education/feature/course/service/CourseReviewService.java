package com.bm.education.feature.course.service;

import com.bm.education.shared.exception.custom.ReviewDuplicateException;
import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.course.model.CourseReview;
import com.bm.education.feature.user.model.User;
import com.bm.education.feature.course.repository.CourseRepository;
import com.bm.education.feature.course.repository.CourseReviewRepository;
import com.bm.education.feature.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public CourseReview createCourseReview(Long courseId, Integer rating, String comment, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (courseReviewRepository.existsByUserAndCourse(user, course)) {
            throw new ReviewDuplicateException(String.format("Course %d already reviewed by user id %d", courseId, user.getId()));
        }

        CourseReview courseReview = CourseReview.builder().user(user).course(course).rating(rating).comment(comment).build();

        return courseReviewRepository.save(courseReview);
    }
}

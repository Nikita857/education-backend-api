package com.bm.education.services;

import com.bm.education.dto.course.CourseReviewDto;
import com.bm.education.exceptions.ReviewDuplicateException;
import com.bm.education.mapper.CourseReviewMapper;
import com.bm.education.models.Course;
import com.bm.education.models.CourseReview;
import com.bm.education.models.User;
import com.bm.education.repositories.CourseRepository;
import com.bm.education.repositories.CourseReviewRepository;
import com.bm.education.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseReviewMapper courseReviewMapper;

    @Transactional
    public CourseReviewDto createCourseReview(Integer courseId, Integer rating, String comment) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (courseReviewRepository.existsByUserAndCourse(user, course)) {
            throw new ReviewDuplicateException(
                    String.format("Course %d already reviewed by user id %d", courseId, user.getId()));
        }

        CourseReview courseReview = CourseReview.builder()
                .user(user)
                .course(course)
                .rating(rating)
                .comment(comment)
                .build();

        CourseReview savedReview = courseReviewRepository.save(courseReview);
        return courseReviewMapper.toDto(savedReview);
    }
}

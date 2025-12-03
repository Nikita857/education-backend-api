package com.bm.education.feature.course.service;

import com.bm.education.feature.course.model.Course;
import com.bm.education.shared.exception.custom.LessonAlreadyCompletedException;
import com.bm.education.feature.lesson.model.Lesson;
import com.bm.education.feature.lesson.repository.LessonRepository;
import com.bm.education.feature.user.repository.UserLessonCompletionRepository;
import com.bm.education.feature.user.repository.UserCoursesRepository;
import com.bm.education.feature.user.model.User;
import com.bm.education.feature.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentProgressService {

    private final UserLessonCompletionRepository userLessonCompletionRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserCoursesRepository userCoursesRepository; // Keep this for existsByUserAndCourse check if needed
    private final com.bm.education.feature.user.repository.UserCourseEnrollmentRepository userCourseEnrollmentRepository;


    @Transactional
    public void markLessonAsCompleted(Long lessonId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        if (userLessonCompletionRepository.existsByUserAndLesson(user, lesson)) {
            throw new LessonAlreadyCompletedException("Lesson already marked as completed");
        }

        com.bm.education.feature.user.model.UserLessonCompletion userLessonCompletion =
            com.bm.education.feature.user.model.UserLessonCompletion.builder()
                .user(user)
                .lesson(lesson)
                .build();

        userLessonCompletionRepository.save(userLessonCompletion);

        checkCourseCompletion(user, lesson.getModule().getCourse());
    }

    private void checkCourseCompletion(User user, Course course) {
        long totalLessonsInCourse = course.getModules().stream()
                .mapToLong(module -> module.getLessons().size())
                .sum();

        long completedLessonsInCourse = userLessonCompletionRepository.countByUserAndLessonModuleCourse(user, course);

        if (totalLessonsInCourse == completedLessonsInCourse) {
            Optional<com.bm.education.feature.user.model.UserCourseEnrollment> existingEnrollment =
                userCourseEnrollmentRepository.findByUserAndCourse(user, course);

            if (existingEnrollment.isPresent()) {
                // Update the enrollment status to COMPLETED
                com.bm.education.feature.user.model.UserCourseEnrollment enrollment = existingEnrollment.get();
                enrollment.setStatus(com.bm.education.feature.user.model.UserCourseEnrollment.EnrollmentStatus.COMPLETED);
                enrollment.setProgressPercentage(100);
                enrollment.setCompletionDate(java.time.Instant.now());
                userCourseEnrollmentRepository.save(enrollment);
            } else {
                // If no enrollment exists, create a new one - this shouldn't happen in normal flow
                com.bm.education.feature.user.model.UserCourseEnrollment enrollment =
                    new com.bm.education.feature.user.model.UserCourseEnrollment(user, course);
                enrollment.setStatus(com.bm.education.feature.user.model.UserCourseEnrollment.EnrollmentStatus.COMPLETED);
                enrollment.setProgressPercentage(100);
                enrollment.setCompletionDate(java.time.Instant.now());
                userCourseEnrollmentRepository.save(enrollment);
            }
        }
    }
}
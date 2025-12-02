package com.bm.education.services;

import com.bm.education.course.model.Course;
import com.bm.education.api.exception.custom.LessonAlreadyCompletedException;
import com.bm.education.lesson.model.Lesson;
import com.bm.education.lesson.repository.LessonRepository;
import com.bm.education.models.*;
import com.bm.education.repositories.*;
import com.bm.education.user.model.User;
import com.bm.education.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentProgressService {

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserCoursesRepository userCoursesRepository; // Keep this for existsByUserAndCourse check if needed
    private final UserCourseCompletionRepository userCourseCompletionRepository;


    @Transactional
    public void markLessonAsCompleted(Integer lessonId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        if (userProgressRepository.existsByUserAndLesson(user, lesson)) {
            throw new LessonAlreadyCompletedException("Lesson already marked as completed");
        }

        UserProgress userProgress = new UserProgress();
        userProgress.setUser(user);
        userProgress.setLesson(lesson);
        userProgress.setCompletedAt(Instant.now());
        userProgress.setStatus(UserProgressStatus.COMPLETED);

        userProgressRepository.save(userProgress);

        checkCourseCompletion(user, lesson.getModule().getCourse());
    }

    private void checkCourseCompletion(User user, Course course) {
        long totalLessonsInCourse = course.getModules().stream()
                .mapToLong(module -> module.getLessons().size())
                .sum();

        long completedLessonsInCourse = userProgressRepository.countByUserAndLesson_Module_Course(user, course);

        if (totalLessonsInCourse == completedLessonsInCourse) {
            Optional<UserCourseCompletion> existingCompletion = userCourseCompletionRepository.findByUserAndCourse(user, course);

            if (existingCompletion.isEmpty()) {
                UserCourseCompletion userCourseCompletion = new UserCourseCompletion(user, course);
                userCourseCompletionRepository.save(userCourseCompletion);
            }
        }
    }
}

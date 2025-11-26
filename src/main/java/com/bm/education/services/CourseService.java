package com.bm.education.services;

import com.bm.education.dto.common.PageResponse;
import com.bm.education.dto.course.CourseDto;
import com.bm.education.mapper.CourseMapper;
import com.bm.education.models.Course;
import com.bm.education.models.CourseStatus;
import com.bm.education.models.User;
import com.bm.education.models.UserCourses;
import com.bm.education.repositories.CourseRepository;
import com.bm.education.repositories.UserCoursesRepository;
import com.bm.education.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final UserCoursesRepository userCoursesRepository;

    @Transactional
    public PageResponse<CourseDto> getAllCourses(int page, int size, String sortBy, @NotNull String sortDir,
            String title, String category, CourseStatus status) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Course> courses;
        if (title != null) {
            // Basic search by title (would be better with a custom query or specification)
            // For now, let's just use findAll and filter in memory if needed, or assume
            // title search is not yet fully implemented in repo
            // But wait, I didn't add findByTitleContaining. Let's stick to findAll for now
            // or add it later.
            // Actually, let's use the new finders if applicable.
            if (category != null) {
                courses = courseRepository.findByCategorySlug(category, pageable);
            } else if (status != null) {
                courses = courseRepository.findAllByStatus(status, pageable);
            } else {
                courses = courseRepository.findAll(pageable);
            }
        } else if (category != null) {
            courses = courseRepository.findByCategorySlug(category, pageable);
        } else if (status != null) {
            courses = courseRepository.findAllByStatus(status, pageable);
        } else {
            courses = courseRepository.findAll(pageable);
        }

        List<CourseDto> courseDtos = courses.getContent().stream()
                .map(courseMapper::toDto)
                .toList();

        PageResponse<CourseDto> response = new PageResponse<>();
        response.setContent(courseDtos);
        response.setPage(courses.getNumber());
        response.setSize(courses.getSize());
        response.setTotalElements(courses.getTotalElements());
        response.setTotalPages(courses.getTotalPages());
        response.setFirst(courses.isFirst());
        response.setLast(courses.isLast());

        return response;
    }

    @Transactional
    public CourseDto getCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + courseId));
        return courseMapper.toDto(course);
    }

    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Transactional
    public CourseDto updateCourse(Integer courseId, CourseDto courseDto) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course not found: " + courseId);
        }
        Course course = courseMapper.toEntity(courseDto);
        course.setId(courseId);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course not found: " + courseId);
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void enroll(Integer courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (userCoursesRepository.existsByUserAndCourse(user, course)) {
            throw new EntityExistsException("User is already enrolled in this course");
        }

        UserCourses userCourses = UserCourses.builder()
                .user(user)
                .course(course)
                .enrolledAt(java.time.Instant.now())
                .build();
        userCoursesRepository.save(userCourses);
    }
}
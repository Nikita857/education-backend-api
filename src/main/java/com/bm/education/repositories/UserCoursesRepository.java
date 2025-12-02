package com.bm.education.repositories;

import com.bm.education.course.model.Course;
import com.bm.education.user.model.User;
import com.bm.education.models.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourses, Integer> {
    boolean existsByUserAndCourse(User user, Course course);

    Optional<UserCourses> findByUserAndCourse(User user, Course course);

    List<UserCourses> findByUser(User user);
}
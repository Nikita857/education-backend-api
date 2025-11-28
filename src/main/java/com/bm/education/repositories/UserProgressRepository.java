package com.bm.education.repositories;

import com.bm.education.models.Course;
import com.bm.education.models.Lesson;
import com.bm.education.models.User;
import com.bm.education.models.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bm.education.dto.report.UserProgressReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Integer> {
    boolean existsByUserAndLesson(User user, Lesson lesson);

    long countByUserAndLesson_Module_Course(User user, Course course);

    @Query("SELECT new com.bm.education.dto.report.UserProgressReport(" +
            "u.id, u.firstName, u.lastName, u.department, u.jobTitle, " +
            "c.id, c.title, m.id, m.title, l.id, l.title, up.completedAt) " +
            "FROM UserProgress up " +
            "JOIN up.user u " +
            "LEFT JOIN up.course c " +
            "LEFT JOIN up.module m " +
            "LEFT JOIN up.lesson l " +
            "WHERE (:department IS NULL OR u.department = :department)")
    Page<UserProgressReport> findUserProgressReports(@Param("department") String department, Pageable pageable);
}
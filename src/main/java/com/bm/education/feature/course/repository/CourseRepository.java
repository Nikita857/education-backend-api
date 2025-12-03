package com.bm.education.feature.course.repository;

import java.util.Optional;

import com.bm.education.feature.course.model.Course;

import com.bm.education.feature.course.model.CourseStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
        Page<Course> findAllByStatus(CourseStatus status,
                                     Pageable pageable);

        @Query("SELECT c FROM Course c WHERE c.category.slug = :slug AND c.status = 'ACTIVE'")
        Page<Course> findByCategorySlug(@org.springframework.data.repository.query.Param("slug") String slug,
                        Pageable pageable);

        @Query("SELECT c FROM Course c JOIN c.tags t WHERE t.name = :tagName AND c.status = 'ACTIVE'")
        Page<Course> findByTagsName(@org.springframework.data.repository.query.Param("tagName") String tagName,
                        Pageable pageable);

        Optional<Course> findBySlug(String slug);
}
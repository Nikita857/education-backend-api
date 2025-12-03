package com.bm.education.feature.module.repository;

import com.bm.education.dto.course.ModuleDto;
import com.bm.education.feature.module.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query("SELECT new com.bm.education.dto.course.ModuleDto(m.id, m.title, m.slug, m.status) " +
            "FROM Module m WHERE m.course.id = :courseId ORDER BY m.id")
    List<ModuleDto> findModulesByCourseId(@Param("courseId") Long courseId);

    List<Module> findByCourseId(Long courseId);
}
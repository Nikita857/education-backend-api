package com.bm.education.module.repository;

import com.bm.education.dto.course.ModuleDto;
import com.bm.education.module.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query("SELECT new com.bm.education.dto.course.ModuleDto(m.id, m.title, m.slug, m.status) " +
            "FROM Module m WHERE m.course.id = :courseId ORDER BY m.id")
    List<ModuleDto> findModulesByCourseId(@Param("courseId") Integer courseId);

    List<Module> findByCourseId(Integer courseId);
}
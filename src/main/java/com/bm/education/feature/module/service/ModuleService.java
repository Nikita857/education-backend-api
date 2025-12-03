package com.bm.education.feature.module.service;

import com.bm.education.dto.course.ModuleDto;
import com.bm.education.feature.module.mapper.ModuleMapper;
import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.module.model.Module;
import com.bm.education.feature.course.repository.CourseRepository;
import com.bm.education.feature.module.repository.ModuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleMapper moduleMapper;

    @Transactional(readOnly = true)
    public List<ModuleDto> getCourseModules(Long courseId) {
        return moduleRepository.findModulesByCourseId(courseId);
    }

    @Transactional
    public ModuleDto createModule(Long courseId, ModuleDto moduleDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Module module = Module.builder()
                .course(course)
                .title(moduleDto.getTitle())
                .slug(moduleDto.getSlug())
                .status(moduleDto.getStatus())
                .build();

        Module savedModule = moduleRepository.save(module);
        return moduleMapper.toDto(savedModule);
    }

    @Transactional
    public ModuleDto updateModule(Long moduleId, ModuleDto moduleDto) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        module.setTitle(moduleDto.getTitle());
        module.setSlug(moduleDto.getSlug());
        module.setStatus(moduleDto.getStatus());

        Module updatedModule = moduleRepository.save(module);
        return moduleMapper.toDto(updatedModule);
    }

    @Transactional
    public void deleteModule(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new EntityNotFoundException("Module not found: " + moduleId);
        }
        moduleRepository.deleteById(moduleId);
    }
}

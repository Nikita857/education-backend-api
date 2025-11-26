package com.bm.education.services;

import com.bm.education.dto.course.LessonDto;
import com.bm.education.mapper.LessonMapper;
import com.bm.education.models.Lesson;
import com.bm.education.models.Module;
import com.bm.education.repositories.LessonRepository;
import com.bm.education.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final LessonMapper lessonMapper;

    @Transactional(readOnly = true)
    public List<LessonDto> getModuleLessons(Integer moduleId) {
        // Verify module exists
        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found");
        }
        return lessonRepository.findByModuleId(moduleId).stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LessonDto createLesson(Integer moduleId, LessonDto lessonDto) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        Lesson lesson = Lesson.builder()
                .module(module)
                .title(lessonDto.getTitle())
                .description(lessonDto.getDescription())
                .textContent(lessonDto.getTextContent())
                .contentType(lessonDto.getContentType())
                .video(lessonDto.getVideo())
                .filePath(lessonDto.getFilePath())
                .contentUrl(lessonDto.getContentUrl())
                .build();

        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    @Transactional
    public LessonDto updateLesson(Integer lessonId, LessonDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setTextContent(lessonDto.getTextContent());
        lesson.setContentType(lessonDto.getContentType());
        lesson.setVideo(lessonDto.getVideo());
        lesson.setFilePath(lessonDto.getFilePath());
        lesson.setContentUrl(lessonDto.getContentUrl());

        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @Transactional
    public void deleteLesson(Integer lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new RuntimeException("Lesson not found: " + lessonId);
        }
        lessonRepository.deleteById(lessonId);
    }
}

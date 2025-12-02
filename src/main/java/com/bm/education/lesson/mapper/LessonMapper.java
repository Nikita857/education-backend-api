package com.bm.education.lesson.mapper;

import com.bm.education.dto.course.LessonDto;
import com.bm.education.lesson.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toDto(Lesson lesson);
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "shortDescription", ignore = true)
    @Mapping(target = "testCode", ignore = true)
    @Mapping(target = "contentLength", ignore = true)
    @Mapping(target = "userProgresses", ignore = true)
    @Mapping(target = "scormPackagePath", ignore = true)
    @Mapping(target = "webinarUrl", ignore = true)
    @Mapping(target = "webinarStartTime", ignore = true)
    @Mapping(target = "webinarDurationMinutes", ignore = true)
    @Mapping(target = "estimatedDurationMinutes", ignore = true)
    @Mapping(target = "viewingProgressRequired", ignore = true)
    @Mapping(target = "viewingPercentageThreshold", ignore = true)
    Lesson toEntity(LessonDto lessonDto);
}

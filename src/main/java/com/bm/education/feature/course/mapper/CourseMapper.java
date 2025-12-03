package com.bm.education.feature.course.mapper;

import com.bm.education.dto.course.CourseDto;
import com.bm.education.feature.course.model.Course;
import com.bm.education.feature.module.mapper.ModuleMapper;
import com.bm.education.feature.tag.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface CourseMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "tags", target = "tagIds")
    CourseDto toDto(Course course);

    @Mapping(target = "userProgresses", ignore = true)
    @Mapping(target = "documentation", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Course toEntity(CourseDto courseDto);

    default List<Long> mapTagsToTagIds(Set<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getId).collect(Collectors.toList());
    }
}

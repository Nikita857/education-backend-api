package com.bm.education.feature.module.mapper;

import com.bm.education.dto.course.ModuleDto;
import com.bm.education.feature.module.model.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    ModuleDto toDto(Module module);
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "userProgresses", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Module toEntity(ModuleDto moduleDto);
}

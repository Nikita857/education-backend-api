package com.bm.education.mapper;

import com.bm.education.dto.onboarding.AdaptationProgramDto;
import com.bm.education.models.AdaptationProgram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OnboardingMapper {

    @Mapping(target = "assignedToUserId", source = "assignedTo.id")
    @Mapping(target = "mentorUserId", source = "mentor.id")
    AdaptationProgramDto toDto(AdaptationProgram program);

    @Mapping(target = "assignedTo.id", source = "assignedToUserId")
    @Mapping(target = "mentor.id", source = "mentorUserId")
    AdaptationProgram toEntity(AdaptationProgramDto dto);
}

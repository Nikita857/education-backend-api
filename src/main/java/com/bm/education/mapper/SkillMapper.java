package com.bm.education.mapper;

import com.bm.education.dto.skill.SkillDto;
import com.bm.education.dto.skill.UserSkillDto;
import com.bm.education.models.Skill;
import com.bm.education.models.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDto toSkillDto(Skill skill);

    @Mapping(target = "userSkills", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Skill toSkillEntity(SkillDto skillDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "skillId", source = "skill.id")
    @Mapping(target = "skillName", source = "skill.name")
    UserSkillDto toUserSkillDto(UserSkill userSkill);
}

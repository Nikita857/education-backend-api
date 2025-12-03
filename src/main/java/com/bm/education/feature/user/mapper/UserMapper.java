package com.bm.education.feature.user.mapper;

import com.bm.education.dto.user.UserDto;
import com.bm.education.feature.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "department", target = "department")
    UserDto toDto(User user);

    @Mapping(source = "department", target = "department")
    @Mapping(target = "qualification", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "offers", ignore = true)
    @Mapping(target = "userProgresses", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "ssoId", ignore = true)
    @Mapping(target = "userSkills", ignore = true)
    @Mapping(target = "individualDevelopmentPlans", ignore = true)
    @Mapping(target = "blogPosts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshTokenExpiryDate", ignore = true)
    User toEntity(UserDto userDto);
}

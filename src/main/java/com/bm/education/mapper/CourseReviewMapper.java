package com.bm.education.mapper;

import com.bm.education.dto.course.CourseReviewDto;
import com.bm.education.models.CourseReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "course.id", target = "courseId")
    CourseReviewDto toDto(CourseReview review);
}

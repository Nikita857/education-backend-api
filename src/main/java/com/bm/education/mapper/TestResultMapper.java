package com.bm.education.mapper;

import com.bm.education.dto.quiz.TestResultDto;
import com.bm.education.models.TestResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestResultMapper {
    @Mapping(source = "test.id", target = "testId")
    @Mapping(source = "user.id", target = "userId")
    TestResultDto toDto(TestResult testResult);
}

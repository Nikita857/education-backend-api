package com.bm.education.mapper;

import com.bm.education.dto.quiz.AnswerOptionDto;
import com.bm.education.dto.quiz.QuestionDto;
import com.bm.education.dto.quiz.QuizDto;
import com.bm.education.models.AnswerOption;
import com.bm.education.models.Question;
import com.bm.education.models.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    @Mapping(target = "questions", source = "questions")
    QuizDto toQuizDto(Test test);

    @Mapping(target = "answerOptions", source = "answerOptions")
    QuestionDto toQuestionDto(Question question);

    // This mapping ensures the 'isCorrect' field is ignored
    @Mapping(target = "id", source = "id")
    @Mapping(target = "optionText", source = "optionText")
    AnswerOptionDto toAnswerOptionDto(AnswerOption answerOption);
}

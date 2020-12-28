package com.Group.Rest.controller.dto.surveyFromFront;

import com.Group.Rest.model.Question;
import com.Group.Rest.model.SurveyQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDtoMapper {
    private QuestionDtoMapper(){}

    public static List<QuestionDto> mapToQuestionDtos(List<SurveyQuestion> surveyQuestions) {
        return surveyQuestions.stream()
                .map(QuestionDtoMapper::mapToQuestionDto)
                .collect(Collectors.toList());
    }

    private static QuestionDto mapToQuestionDto(SurveyQuestion surveyQuestion) {
        return QuestionDto.builder()
                .questionId(surveyQuestion.getSurveyQuestionId().getQuestionId())
                .questionPosition(surveyQuestion.getQuestionPosition())
                .questionName(surveyQuestion.getQuestion().getQuestionName())
                .questionType(surveyQuestion.getQuestion().getQuestionType())
                .chartType(surveyQuestion.getQuestion().getChartType())
                .questionAnswers(AnswerDtoMapper.mapToAnswersDtos(surveyQuestion.getQuestion().getQuestionAnswers()))
                .build();
    }

    public static QuestionDto mapToQuestionDto(Question question) {
        return QuestionDto.builder()
                .questionId(question.getQuestionId())
                .questionName(question.getQuestionName())
                .questionType(question.getQuestionType())
                .chartType(question.getChartType())
                .questionAnswers(AnswerDtoMapper.mapToAnswersDtos(question.getQuestionAnswers()))
                .build();
    }


    public static Question mapToQuestion(QuestionDto questionDto, Long questionId) {
        return Question.builder()
                .questionId(questionId)
                .questionName(questionDto.getQuestionName())
                .questionType(questionDto.getQuestionType())
                .chartType(questionDto.getChartType())
                .questionAnswers(AnswerDtoMapper.mapToQuestionAnswers(questionDto.getQuestionAnswers()))
                .build();
    }
}

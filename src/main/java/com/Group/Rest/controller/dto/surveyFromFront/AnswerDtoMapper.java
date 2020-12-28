package com.Group.Rest.controller.dto.surveyFromFront;

import com.Group.Rest.controller.dto.completedSurvey.CompletedAnswerDto;
import com.Group.Rest.model.Answer;
import com.Group.Rest.model.QuestionAnswer;

import java.util.List;
import java.util.stream.Collectors;

import static com.Group.Rest.model.Answer.AnswerId;


public class AnswerDtoMapper {
    private AnswerDtoMapper(){}

    public static QuestionAnswer mapToQuestionAnswerName(AnswerDto answerDto) {
        return QuestionAnswer.builder()
                .answerName(answerDto.getAnswerName())
                .build();
    }

    public static List<AnswerDto> mapToAnswersDtos(List<QuestionAnswer> questionAnswers) {
        return questionAnswers.stream()
                .map(questionAnswer -> mapToAnswersDto(questionAnswer))
                .collect(Collectors.toList());
    }

    private static AnswerDto mapToAnswersDto(QuestionAnswer questionAnswer) {
        return AnswerDto.builder()
                .answerId(questionAnswer.getQuestionAnswerId())
                .answerName(questionAnswer.getAnswerName())
                .build();
    }

    public static List<Answer> mapToAnswers(List<CompletedAnswerDto> answers, Long surveyResponseId) {
        return answers.stream()
                .map(completedAnswerDto -> mapToAnswer(completedAnswerDto, surveyResponseId))
                .collect(Collectors.toList());
    }

    private static Answer mapToAnswer(CompletedAnswerDto completedAnswerDto, Long surveyResponseId) {
        return Answer.builder()
                .answer(completedAnswerDto.getAnswer())
                .answerId(mapToAnswerId(completedAnswerDto, surveyResponseId))
                .build();
    }

    private static AnswerId mapToAnswerId(CompletedAnswerDto completedAnswerDto, Long surveyResponseId) {
        return AnswerId.builder()
                .questionId(completedAnswerDto.getQuestionId())
                .surveyResponseId(surveyResponseId)
                .build();
    }

    public static List<QuestionAnswer> mapToQuestionAnswers(List<AnswerDto> answerDtos) {
        return answerDtos.stream().map(questionAnswer -> mapToQuestionAnswer(questionAnswer)).collect(Collectors.toList());
    }
    private static QuestionAnswer mapToQuestionAnswer(AnswerDto answerDto) {
        return QuestionAnswer.builder()
                .answerName(answerDto.getAnswerName())
                .build();
    }

}

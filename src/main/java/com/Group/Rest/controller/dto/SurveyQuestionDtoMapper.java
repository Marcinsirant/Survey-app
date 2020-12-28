package com.Group.Rest.controller.dto;

import com.Group.Rest.model.SurveyQuestion;

import static com.Group.Rest.model.SurveyQuestion.*;

public class SurveyQuestionDtoMapper {
    private SurveyQuestionDtoMapper(){}

    public static SurveyQuestion mapToQuestionSurvey(SurveyQuestionDto surveyQuestionDto){
        return builder()
                .questionPosition(surveyQuestionDto.getQuestionPosition())
                .surveyQuestionId(new SurveyQuestionId(surveyQuestionDto.getSurveyId(), surveyQuestionDto.getQuestionId()))
                .build();
    }
}

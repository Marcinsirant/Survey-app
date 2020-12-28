package com.Group.Rest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SurveyQuestionDto {

    @NotNull
    private String surveyId;
    @NotNull
    private Long questionId;
    @NotNull
    private Long questionPosition;


}

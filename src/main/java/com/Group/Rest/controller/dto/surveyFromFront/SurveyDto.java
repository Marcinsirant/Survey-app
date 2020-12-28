package com.Group.Rest.controller.dto.surveyFromFront;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class SurveyDto {

    private String surveyId;
    @NotNull
    private String surveyName;
    @NotNull
    private String surveyDescription;
    @NotNull
    private List<QuestionDto> questions;

    private LocalDateTime surveyStart;

    private LocalDateTime surveyStop;
}
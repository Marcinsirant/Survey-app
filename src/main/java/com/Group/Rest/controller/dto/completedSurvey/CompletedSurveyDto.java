package com.Group.Rest.controller.dto.completedSurvey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletedSurveyDto implements Serializable {

    @NotNull
    private String surveyId;
    @NotNull
    List<CompletedAnswerDto> answers;

}

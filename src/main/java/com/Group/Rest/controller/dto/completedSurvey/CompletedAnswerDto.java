package com.Group.Rest.controller.dto.completedSurvey;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletedAnswerDto implements Serializable {

    private Long questionId;

    private String answer;

}

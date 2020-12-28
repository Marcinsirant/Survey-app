package com.Group.Rest.controller.dto.surveyFromFront;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class QuestionDto {

        @NotNull
        private Long questionId;
        @NotNull
        private Long questionPosition;
        @NotNull
        private String questionName;
        @NotNull
        private String questionType;
        @NotNull
        private String chartType;

        List<AnswerDto> questionAnswers;
}

package com.Group.Rest.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyQuestion {
    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SurveyQuestionId implements Serializable {

        private String surveyId;

        private Long questionId;

    }
    @EmbeddedId
    private SurveyQuestionId surveyQuestionId;

    private Long questionPosition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId", updatable = false, insertable = false)
    private Question question;
}

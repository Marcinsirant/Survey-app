package com.Group.Rest.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AnswerId implements Serializable {

        private Long questionId;

        private Long surveyResponseId;

    }

    @EmbeddedId
    private AnswerId answerId;

    private String answer;
}

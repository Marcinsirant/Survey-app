package com.Group.Rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    @Id
    private String surveyId;

    private String surveyName;

    private Long surveyCategoryId;

    private String surveyDescription;

    private LocalDateTime surveyStart;

    private LocalDateTime surveyStop;
}

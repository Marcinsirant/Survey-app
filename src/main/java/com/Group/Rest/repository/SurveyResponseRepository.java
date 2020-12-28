package com.Group.Rest.repository;

import com.Group.Rest.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    List<SurveyResponse> findBySurveyId(String surveyId);

    void deleteBySurveyId(String surveyId);
}

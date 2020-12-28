package com.Group.Rest.repository;


import com.Group.Rest.model.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.Group.Rest.model.SurveyQuestion.*;

@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, SurveyQuestionId> {

    @Query("select sq from SurveyQuestion sq join fetch sq.question where sq.surveyQuestionId.surveyId = :idSurvey")
    List<SurveyQuestion> findBySurveyQuestionId_surveyId(@Param("idSurvey") String idSurvey);

    @Override
    @Query("select distinct sq from SurveyQuestion sq join fetch sq.question")
    List<SurveyQuestion> findAll();

    void deleteBySurveyQuestionId_SurveyId(String surveyId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SurveyQuestion s WHERE s.surveyQuestionId.surveyId = ?1 AND s.surveyQuestionId.questionId = ?2")
    void deleteAllBySurveyQuestionId_SurveyIdAndSurveyQuestionId_QuestionId(String surveyId, Long questionId);

    Optional<SurveyQuestion> findOneBySurveyQuestionId_QuestionId(Long questionId);

    Optional<SurveyQuestion> findOneBySurveyQuestionId_QuestionIdAndSurveyQuestionId_SurveyId(Long questionId, String surveyId);

}

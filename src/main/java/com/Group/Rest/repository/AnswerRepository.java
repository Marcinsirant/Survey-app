package com.Group.Rest.repository;


import com.Group.Rest.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.Group.Rest.model.Answer.*;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {

    List<Answer> findByAnswerId_SurveyResponseIdIn(List<Long> surveyResponseIds);

    @Modifying
    @Query("DELETE FROM Answer a WHERE a.answerId.questionId = ?1")
    void deleteAllByAnswerId_QuestionId(Long questionId);

    void deleteByAnswerId_SurveyResponseIdIn(List<Long> surveyResponseIds);

}

package com.Group.Rest.repository;

import com.Group.Rest.model.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    @Modifying
    @Query("DELETE FROM QuestionAnswer q WHERE q.questionId = ?1")
    void deleteAllByQuestionId(Long questionId);

    List<QuestionAnswer> findByQuestionId(Long questionId);
}

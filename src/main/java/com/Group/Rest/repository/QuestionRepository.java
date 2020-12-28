package com.Group.Rest.repository;

import com.Group.Rest.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuestionIdIn(List<Long> collect);

    @Modifying
    @Query("DELETE FROM Question q WHERE q.questionId = ?1")
    void deleteAllByQuestionId(Long questionId);


}

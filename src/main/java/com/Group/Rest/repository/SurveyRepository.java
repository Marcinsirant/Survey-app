package com.Group.Rest.repository;


import com.Group.Rest.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, String> {
    Optional<Survey> findOneBySurveyId(String SurveyId);
}

package com.Group.Rest.service;

import com.Group.Rest.controller.dto.completedSurvey.CompletedSurveyDto;
import com.Group.Rest.controller.dto.surveyFromFront.AnswerDtoMapper;
import com.Group.Rest.model.Answer;
import com.Group.Rest.model.QuestionAnswer;
import com.Group.Rest.model.SurveyResponse;
import com.Group.Rest.repository.AnswerRepository;
import com.Group.Rest.repository.QuestionAnswerRepository;
import com.Group.Rest.repository.SurveyRepository;
import com.Group.Rest.repository.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyCompletedService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final AnswerRepository answerRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final SurveyRepository surveyRepository;

    @Transactional
    public CompletedSurveyDto addCompletedSurvey(CompletedSurveyDto completedSurveyDto, String surveyId) {
        log.info("add completed survey {}", surveyId);
        surveyRepository.findOneBySurveyId(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Survey does not exist"));
        SurveyResponse surveyResponse = new SurveyResponse(null, surveyId, LocalDateTime.now(), true);
        surveyResponseRepository.save(surveyResponse);
//        valid
        completedSurveyDto.getAnswers().forEach(completedAnswerDto -> {
            QuestionAnswer questionAnswers = questionAnswerRepository.findByQuestionId(completedAnswerDto.getQuestionId())
                    .stream()
                    .filter(questionAnswer -> questionAnswer.getAnswerName().equals(completedAnswerDto.getAnswer()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Error with valid"));
            log.info("Add answer {}",questionAnswers);
        });
//
        List<Answer> answers = AnswerDtoMapper.mapToAnswers(completedSurveyDto.getAnswers(), surveyResponse.getSurveyResponseId());
        answers.forEach(answerRepository::save);
        return completedSurveyDto;
    }

}

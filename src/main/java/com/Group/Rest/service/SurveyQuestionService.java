package com.Group.Rest.service;

import com.Group.Rest.controller.dto.SurveyQuestionDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDtoMapper;
import com.Group.Rest.controller.dto.surveyFromFront.SurveyDto;
import com.Group.Rest.model.Survey;
import com.Group.Rest.repository.SurveyQuestionRepository;
import com.Group.Rest.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.Group.Rest.controller.dto.SurveyQuestionDtoMapper.mapToQuestionSurvey;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyQuestionService {
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final SurveyRepository surveyRepository;

    public SurveyDto getQuestionsWithAnswers(String surveyId) {
        log.info("get question with snswers {}",surveyId);
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new IllegalArgumentException("Survey is not existed"));
        List<QuestionDto> questionDtos = QuestionDtoMapper.mapToQuestionDtos(surveyQuestionRepository.findBySurveyQuestionId_surveyId(surveyId));
        return new SurveyDto(surveyId, survey.getSurveyName(), survey.getSurveyDescription(), questionDtos.stream()
                .sorted((el1, el2) -> (int) (el1.getQuestionPosition() - el2.getQuestionPosition()))
                .collect(Collectors.toList()),null,null);
    }

    public void setSurveyQuestionPosition(String surveyId, List<SurveyQuestionDto> surveyQuestionDtos) {
        log.info("set question position in survey {}",surveyId);
        surveyQuestionDtos.forEach(surveyQuestion -> surveyQuestionRepository.save(mapToQuestionSurvey(surveyQuestion)));
    }
}

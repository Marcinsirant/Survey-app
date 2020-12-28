package com.Group.Rest.service;

import com.Group.Rest.controller.dto.page.PageDto;
import com.Group.Rest.controller.dto.surveyFromFront.AnswerDtoMapper;
import com.Group.Rest.controller.dto.surveyFromFront.SurveyDto;
import com.Group.Rest.model.*;
import com.Group.Rest.model.SurveyQuestion.SurveyQuestionId;
import com.Group.Rest.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Struct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final QuestionRepository questionRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final AnswerRepository answerRepository;

    public PageDto<List<Survey>> getSurveys(int page, int numberPerPage) {
        Page<Survey> surveyPage = surveyRepository.findAll(PageRequest.of(page, numberPerPage, Sort.by(Sort.Direction.ASC, "surveyId")));
        surveyPage.getTotalElements();
        surveyPage.getTotalPages();
        PageDto<List<Survey>> pageDto = new PageDto<List<Survey>>(surveyPage.stream()
                .collect(Collectors.toList()), surveyPage.getTotalElements(),surveyPage.getTotalPages());
        return pageDto;
    }

    public Survey getSurvey(String surveyId) {
        return surveyRepository.findOneBySurveyId(surveyId).orElseThrow(()->new IllegalArgumentException("Survey does not exist"+surveyId));
    }

    @Transactional
    public void deleteSurvey(String surveyId) {
        log.info("delete survey {}", surveyId);
        List<Long> Ids = surveyResponseRepository.findBySurveyId(surveyId)
                .stream()
                .map(SurveyResponse::getSurveyResponseId)
                .collect(Collectors.toList());
        answerRepository.deleteByAnswerId_SurveyResponseIdIn(Ids);
        surveyResponseRepository.deleteBySurveyId(surveyId);
        surveyQuestionRepository.deleteBySurveyQuestionId_SurveyId(surveyId);
        surveyRepository.deleteById(surveyId);
    }

    public static String generateRandomString(int length, String text) {
        text = text.replaceAll("\\s+", "");
        SecureRandom random = new SecureRandom();
        if (length < 1) throw new IllegalArgumentException();
        if (text.length() < 10) {
            text = "qwertyuiopasdfghjklzxcvbnm1234567890";
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(text.length());
            char rndChar = text.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();

    }

    @Transactional
    public List<Long> addSurveyWithQuestions(SurveyDto surveyDto) {
        log.info("add survey with question {}", surveyDto);
        String surveyId;
        if (surveyRepository.findOneBySurveyId(surveyDto.getSurveyId()).isEmpty()) {
            surveyId = generateRandomString(10, surveyDto.getSurveyName());
        } else {
            surveyId = surveyDto.getSurveyId();
        }


        Survey newSurvey = new Survey(surveyId, surveyDto.getSurveyName(), null, surveyDto.getSurveyDescription(),surveyDto.getSurveyStart(),surveyDto.getSurveyStop());
        Map<Long, Question> questions = new HashMap<>();
        surveyDto.getQuestions().forEach((fQuestions) -> {
            List<QuestionAnswer> newQuestionAnswers = fQuestions.getQuestionAnswers().stream()
                    .map(AnswerDtoMapper::mapToQuestionAnswerName)
                    .collect(Collectors.toList());

            Question question = new Question(null, fQuestions.getQuestionType(), fQuestions.getQuestionName(), fQuestions.getChartType(), newQuestionAnswers);
            questions.put(fQuestions.getQuestionPosition(), question);
        });

        surveyRepository.save(newSurvey);
        questions.forEach((Long, question) -> {
            questionRepository.save(question);
            SurveyQuestionId surveyQuestionId = new SurveyQuestionId(newSurvey.getSurveyId(), question.getQuestionId());
            SurveyQuestion newSurveyQuestion = new SurveyQuestion(surveyQuestionId, Long, null);
            surveyQuestionRepository.save(newSurveyQuestion);
        });

        return questions.values().stream().map(Question::getQuestionId).collect(Collectors.toList());
    }

}

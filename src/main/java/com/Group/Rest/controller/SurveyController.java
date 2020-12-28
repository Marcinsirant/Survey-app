package com.Group.Rest.controller;

import com.Group.Rest.controller.dto.completedSurvey.CompletedSurveyDto;
import com.Group.Rest.controller.dto.page.PageDto;
import com.Group.Rest.controller.dto.surveyFromFront.SurveyDto;
import com.Group.Rest.model.Survey;
import com.Group.Rest.service.QuestionService;
import com.Group.Rest.service.SurveyCompletedService;
import com.Group.Rest.service.SurveyQuestionService;
import com.Group.Rest.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyQuestionService surveyQuestionService;
    private final SurveyCompletedService surveyCompletedService;
    private final QuestionService questionService;


    @GetMapping("/surveys")
    public PageDto<List<Survey>> getSurveys(@RequestParam(required = false)int page, @RequestParam(required = false)int numberPerPage) {
        int pageNumber = page >= 0 ? page : 0;
        numberPerPage = numberPerPage >= 0 ? numberPerPage : 0;
        return surveyService.getSurveys(pageNumber, numberPerPage);
    }

    @GetMapping("/surveys/{id}/questions")
    public SurveyDto getSurveyQuestionWithName(@PathVariable String id) {
        return surveyQuestionService.getQuestionsWithAnswers(id);
    }

    @PostMapping("/surveys")
    public List<Long> addSurveyWithQuestions(@RequestBody SurveyDto surveyDto) {
        return surveyService.addSurveyWithQuestions(surveyDto);
    }

    @PostMapping("/surveys/{id}")
    public CompletedSurveyDto addCompletedSurvey(@PathVariable String id, @RequestBody CompletedSurveyDto completedSurveyDto) {
        return surveyCompletedService.addCompletedSurvey(completedSurveyDto, id);
    }

    @DeleteMapping("/surveys/{id}/questions/{questionId}")
    public void deleteQuestion(@PathVariable String id, @PathVariable Long questionId) {
        questionService.deleteQuestion(id, questionId);
    }

    @DeleteMapping("/surveys/{id}")
    public void deleteSurvey(@PathVariable String id) {
        surveyService.deleteSurvey(id);
    }

}

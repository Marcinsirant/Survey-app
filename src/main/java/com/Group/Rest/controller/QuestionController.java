package com.Group.Rest.controller;

import com.Group.Rest.controller.dto.SurveyQuestionDto;
import com.Group.Rest.controller.dto.page.PageDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDto;
import com.Group.Rest.service.QuestionService;
import com.Group.Rest.service.SurveyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final SurveyQuestionService surveyQuestionService;

    @GetMapping("/questions")
    public PageDto<List<QuestionDto>> getQuesions(@RequestParam(required = false)int page, @RequestParam(required = false)int numberPerPage) {
        int pageNumber = page >= 0 ? page : 0;
        numberPerPage = numberPerPage >= 0 ? numberPerPage : 0;
        return questionService.getQuestions(pageNumber, numberPerPage);
    }

    @PutMapping("questions/{questionId}")
    public void editQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        questionService.editQuestionInAllSurveys(questionId, questionDto);
    }

    @PutMapping("/surveys/{id}/questions/{questionId}")
    public Long editQuestionDeleteInsert(@PathVariable String id, @PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        return questionService.editQuestionDeleteInsert(id, questionId, questionDto);
    }

    @PutMapping("/surveys/{id}/questions/orders")
    public void setSurveyQuestionPosition(@PathVariable String id ,@RequestBody List<SurveyQuestionDto> surveyQuestionDtos) {
        surveyQuestionService.setSurveyQuestionPosition(id, surveyQuestionDtos);
    }
}

package com.Group.Rest.service;

import com.Group.Rest.controller.dto.page.PageDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDtoMapper;
import com.Group.Rest.model.Question;
import com.Group.Rest.model.SurveyQuestion;
import com.Group.Rest.repository.AnswerRepository;
import com.Group.Rest.repository.QuestionAnswerRepository;
import com.Group.Rest.repository.QuestionRepository;
import com.Group.Rest.repository.SurveyQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Group.Rest.model.SurveyQuestion.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long editQuestionDeleteInsert(String surveyId, Long questionId, QuestionDto questionDto) {
        log.info("edit question {} in survey {}", surveyId,questionId);
        surveyQuestionRepository.findOneBySurveyQuestionId_QuestionIdAndSurveyQuestionId_SurveyId(questionId, surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Question does not exist in the survey"));
        deleteQuestion(surveyId, questionId);
        Question newQuestion = QuestionDtoMapper.mapToQuestion(questionDto, null);
        questionRepository.save(newQuestion);
        surveyQuestionRepository.save(new SurveyQuestion(new SurveyQuestionId(surveyId, newQuestion.getQuestionId()), questionDto.getQuestionPosition(), null));
        return newQuestion.getQuestionId();
    }


    @Transactional
    public void editQuestionInAllSurveys(Long questionId, QuestionDto questionDto) {
        log.info("edit question {} in all survey", questionId);
        answerRepository.deleteAllByAnswerId_QuestionId(questionId);
        surveyQuestionRepository.findOneBySurveyQuestionId_QuestionId(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question does not exist in the survey"));
        Question question = QuestionDtoMapper.mapToQuestion(questionDto, questionId);
        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(String surveyId, Long questionId) {
        surveyQuestionRepository.deleteAllBySurveyQuestionId_SurveyIdAndSurveyQuestionId_QuestionId(surveyId, questionId);
        Optional<SurveyQuestion> surveyQuestion = surveyQuestionRepository.findOneBySurveyQuestionId_QuestionId(questionId);
        if (surveyQuestion.isEmpty()) {
            answerRepository.deleteAllByAnswerId_QuestionId(questionId);
            questionAnswerRepository.deleteAllByQuestionId(questionId);
            questionRepository.deleteAllByQuestionId(questionId);
        }
    }


    public PageDto<List<QuestionDto>> getQuestions(int page, int numberPerPage) {
        log.info("get questions");
        Page<Question> questionDtos = questionRepository.findAll(PageRequest.of(page, numberPerPage, Sort.by(Sort.Direction.ASC, "questionId")));
        PageDto<List<QuestionDto>> pageDto = new PageDto<List<QuestionDto>>(questionDtos.stream()
                .map(QuestionDtoMapper::mapToQuestionDto)
                .collect(Collectors.toList()), questionDtos.getTotalElements(),questionDtos.getTotalPages());
        return pageDto;
    }

}

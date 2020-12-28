package com.Group.Rest.service;

import com.Group.Rest.controller.dto.page.PageDto;
import com.Group.Rest.controller.dto.surveyFromFront.QuestionDto;
import com.Group.Rest.model.Question;
import com.Group.Rest.model.QuestionAnswer;
import com.Group.Rest.model.SurveyQuestion;
import com.Group.Rest.repository.AnswerRepository;
import com.Group.Rest.repository.QuestionAnswerRepository;
import com.Group.Rest.repository.QuestionRepository;
import com.Group.Rest.repository.SurveyQuestionRepository;
import org.junit.Assert;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class QuestionServiceTest {
    @Mock
    QuestionRepository questionRepository;

    @Mock
    SurveyQuestionRepository surveyQuestionRepository;

    @Mock
    AnswerRepository answerRepository;

    @Mock
    QuestionAnswerRepository questionAnswerRepository;

    @InjectMocks
    QuestionService questionService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Question> prepareData() {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<QuestionAnswer> questionAnswers = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                QuestionAnswer questionAnswer = new QuestionAnswer((long) i + j, (long) i, "opt", "answer " + j);
                questionAnswers.add(questionAnswer);
            }
            questions.add(new Question((long) i, "type", "question " + i, "Input", questionAnswers));
        }
        return questions;
    }

    @Test
    void getQuesions_data() {
        given(questionRepository.findAll()).willReturn(prepareData());

        PageDto<List<QuestionDto>> questionDtos = questionService.getQuestions(0,10);


        Assert.assertEquals(questionDtos.getContent().get(0).getQuestionId(), 0L, 0);
        Assert.assertEquals(questionDtos.getContent().get(0).getChartType(), "Input");
        Assert.assertEquals(questionDtos.getContent().get(0).getQuestionType(), "type");
        Assert.assertEquals(questionDtos.getContent().get(0).getQuestionName(), "question 0");
        Assert.assertNotEquals(questionDtos.getContent().get(0).getQuestionAnswers(), null);
    }

    @Test
    void getQuesions_size() {
        given(questionRepository.findAll()).willReturn(prepareData());

        PageDto<List<QuestionDto>> questionDtos = questionService.getQuestions(0, 10);

        Assert.assertThat(questionDtos.getContent(), hasSize(10));
    }

    @Test
    void editQuestionDeleteInsert_throwException() {

        Assertions.assertThrows(IllegalArgumentException.class, ()->questionService.editQuestionDeleteInsert("h1",1L, QuestionDto.builder().build()));
    }


    @Test
    void editQuestionInAllSurveys_throwException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> questionService.editQuestionInAllSurveys(4L, any(QuestionDto.class)));
    }

    @Test
    void deleteQuestion_deleteFromSurveyQuestion() {
        //given
        given(surveyQuestionRepository.findOneBySurveyQuestionId_QuestionId(anyLong())).willReturn(Optional.of(new SurveyQuestion()));
        //when
        questionService.deleteQuestion("h1", 1L);
        //then

        verify(surveyQuestionRepository, times(1)).deleteAllBySurveyQuestionId_SurveyIdAndSurveyQuestionId_QuestionId("h1",1L);
    }

    @Test
    void deleteQuestion_inAllTable() {
        //given

        //when
        questionService.deleteQuestion("h1", 1L);
        //then
        verify(surveyQuestionRepository, times(1)).deleteAllBySurveyQuestionId_SurveyIdAndSurveyQuestionId_QuestionId("h1",1L);
        verify(answerRepository, times(1)).deleteAllByAnswerId_QuestionId(1L);
        verify(questionAnswerRepository, times(1)).deleteAllByQuestionId(1L);
        verify(questionRepository, times(1)).deleteAllByQuestionId(1L);
    }

}

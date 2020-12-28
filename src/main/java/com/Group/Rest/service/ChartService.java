package com.Group.Rest.service;

import com.Group.Rest.controller.dto.Chart;
import com.Group.Rest.model.Answer;
import com.Group.Rest.model.Question;
import com.Group.Rest.model.QuestionAnswer;
import com.Group.Rest.model.SurveyResponse;
import com.Group.Rest.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public List<Chart> getCharts(String surveyId) {
        log.info("get chart for survey {}", surveyId);
        surveyRepository.findOneBySurveyId(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Survey does not exist"));

        List<Chart> charts = new ArrayList<>();
        List<Long> longs = surveyResponseRepository.findBySurveyId(surveyId)
                .stream()
                .map(SurveyResponse::getSurveyResponseId)
                .collect(Collectors.toList());

        List<Answer> answers = answerRepository.findByAnswerId_SurveyResponseIdIn(longs);
        List<Question> questions = questionRepository.findByQuestionIdIn(answers.stream()
                .map(answer -> answer.getAnswerId().getQuestionId())
                .distinct()
                .collect(Collectors.toList()));

        Map<Long, Chart> mapOfCharts = new HashMap<>();

        answers.forEach(answer -> {
            if (!mapOfCharts.containsKey(answer.getAnswerId().getQuestionId())) {
                addNewChartToMap(questions, mapOfCharts, answer);
            }
            countResult(mapOfCharts, answer);
        });

        mapOfCharts.forEach((aLong, chart) -> charts.add(chart));
        return charts;
    }

    private void addNewChartToMap(List<Question> questions, Map<Long, Chart> mapOfCharts, Answer answer) {
        Chart chart = new Chart();
        for (Question question : questions) {
            if (question.getQuestionId().equals(answer.getAnswerId().getQuestionId())) {
                chart.setQuestion(question.getQuestionName());
                chart.setChartType(question.getChartType());
                List<String> possibleAnswers = question.getQuestionAnswers().stream().map(QuestionAnswer::getAnswerName).collect(Collectors.toList());
                chart.setAnswers(possibleAnswers);
                Long[] results = new Long[possibleAnswers.size()];
                Arrays.fill(results, 0L);
                chart.setResults(results);
                mapOfCharts.put(answer.getAnswerId().getQuestionId(), chart);
            }
        }
    }

    private void countResult(Map<Long, Chart> mapOfCharts, Answer answer) {
        Chart chart = mapOfCharts.get(answer.getAnswerId().getQuestionId());
        for (int w = 0; w < chart.getAnswers().size(); w++) {
            if (chart.getAnswers().get(w).equals(answer.getAnswer())) {
                chart.getResults()[w] += 1;
                break;
            }
        }
    }

}

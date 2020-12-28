package com.Group.Rest.websocket;

import com.Group.Rest.controller.dto.Chart;
import com.Group.Rest.controller.dto.completedSurvey.CompletedSurveyDto;
import com.Group.Rest.model.Survey;
import com.Group.Rest.service.ChartService;
import com.Group.Rest.service.SurveyCompletedService;
import com.Group.Rest.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LiveSurveyController {

    @Autowired
    SurveyCompletedService surveyCompletedService;
    @Autowired
    ChartService chartService;
    @Autowired
    SurveyService surveyService;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/test") //powinien byc taki sam jak w endPoint
    //@SendTo("/prefix/messages") //  config.enableSimpleBroker("/prefix");
    public void greeting(CompletedSurveyDto completedSurveyDto) throws Exception {
        Survey survey = surveyService.getSurvey(completedSurveyDto.getSurveyId());
        System.out.println(" Survey in websocket");
        if(survey.getSurveyStop() != null && survey.getSurveyStart() != null){
            System.out.println(" Survey in time");
            log.info(" Survey start: {}", survey.getSurveyStart());
            log.info(" Survey stop: {}", survey.getSurveyStop());
            if(LocalDateTime.now().isAfter(survey.getSurveyStart()) &&  LocalDateTime.now().isBefore(survey.getSurveyStop()) ){
                log.info(" Survey is active");
                System.out.println(" Survey is active");
                surveyCompletedService.addCompletedSurvey(completedSurveyDto, completedSurveyDto.getSurveyId());
                List<Chart> charts = new ArrayList<>(chartService.getCharts(completedSurveyDto.getSurveyId()));/*completedSurveyDto.getSurveyId())*/
                template.convertAndSend("/prefix/messages/"+completedSurveyDto.getSurveyId(), charts);
            }else {
                log.info(" Survey is not active");
                System.out.println(" Survey is not active");
            }
        }
    }

    @MessageExceptionHandler
    @SendToUser("/topic/error")
    public String handleException(RuntimeException ex) {
        log.info("bad surveyId or answer is out of time", ex);
        return "You can not answer the survey";
    }
}
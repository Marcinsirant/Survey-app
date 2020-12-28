package com.Group.Rest.controller;

import com.Group.Rest.controller.dto.Chart;
import com.Group.Rest.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/surveys/{surveyId}/charts")
    public List<Chart> getCharts(@PathVariable String surveyId) {
        return chartService.getCharts(surveyId);
    }

}

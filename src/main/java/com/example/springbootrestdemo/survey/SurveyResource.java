package com.example.springbootrestdemo.survey;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SurveyResource {



    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        super();
        this.surveyService = surveyService;
    }

    @RequestMapping("/surveys")
    public List<Survey>  retrieveAllSurveys(){
        return surveyService.retrieveAllSurveys();

    }

    @RequestMapping("/surveys/{surveyId}")
    public List<Survey> retrieveSurveyById(@PathVariable String surveyId){
        return surveyService.retrieveSurveyById(surveyId);
    }
}

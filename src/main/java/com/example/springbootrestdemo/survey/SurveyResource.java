package com.example.springbootrestdemo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Survey retrieveSurveyById(@PathVariable String surveyId){
        Survey survey = surveyService.retrieveSurveyById(surveyId);

        if(survey==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return survey;
    }

    @RequestMapping(value = "/surveys/{surveyId}/questions")
    public List<Question> retrieveAllQuestionsBySurveyId(@PathVariable String surveyId){
        List<Question> questions = surveyService.retrieveAllQuestionsBySurveyId(surveyId);
        if(questions==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return questions;
    }

    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestionByQuestionId(@PathVariable String surveyId,@PathVariable String questionId){
        Question question = surveyService.retrieveQuestionByQuestionId(surveyId,questionId);
        if(question == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
        return question;

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions",method = RequestMethod.POST)
    public void addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){
      surveyService.addNewSurveyQuestion(surveyId,question);
    }
}

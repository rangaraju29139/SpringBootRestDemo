package com.example.springbootrestdemo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){
     String questionId =  surveyService.addNewSurveyQuestion(surveyId,question);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();

      return ResponseEntity.created(location).build();
    }

    @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId){
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }
}

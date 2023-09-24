package com.example.springbootrestdemo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();
    static{
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        Survey survey2 = new Survey("Survey2", "My Favorite Survey2",
                "Description of the Survey2", questions);

        surveys.add(survey);
        surveys.add(survey2);
    }

    public  List<Question> retrieveAllQuestionsBySurveyId(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);
        if(survey==null) {return null;}
        return survey.getQuestions();

    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equalsIgnoreCase(surveyId);

        Optional<Survey> optionalSurvey
                = surveys.stream().filter(predicate).findFirst();

        if(optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get();
    }

    public Question retrieveQuestionByQuestionId(String surveyId, String questionId) {
        Survey survey = retrieveSurveyById(surveyId);
        if(survey == null) return null;
        Predicate<? super Question> questionPredicate = (question) -> question.getId().equals(questionId);


        Optional<Question> question = survey.getQuestions().stream().filter(questionPredicate).findFirst();
        if(question.isPresent()) {
            return question.get();
        }
        return null;

    }


    public String addNewSurveyQuestion(String surveyId, Question question) {
        List<Question> questions =retrieveAllQuestionsBySurveyId(surveyId);
        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32,secureRandom).toString();
        question.setId(randomId);
        questions.add(question);
        return randomId;
    }

    public String deleteSurveyQuestion(String surveyId, String questionId) {

        List<Question> surveyQuestions = retrieveAllQuestionsBySurveyId(surveyId);

        if (surveyQuestions == null)
            return null;


        Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
        boolean removed = surveyQuestions.removeIf(predicate);

        if(!removed) return null;

        return questionId;
    }
}

package com.example.springbootrestdemo.survey;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public List<Survey> retrieveSurveyById(String surveyId) {
        return surveys.stream().filter((survey -> survey.getId().equalsIgnoreCase(surveyId))).toList();
    }
}

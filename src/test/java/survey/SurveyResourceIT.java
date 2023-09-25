package survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.springbootrestdemo.SpringBootRestDemoApplication;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = SpringBootRestDemoApplication.class)
public class SurveyResourceIT {

    //http://localhost:8080/surveys/Survey1/questions/Question1
    String str = """
			{
			  "id": "Question1",
			  "description": "Most Popular Cloud Platform Today",
			  "options": [
			    "AWS",
			    "Azure",
			    "Google Cloud",
			    "Oracle Cloud"
			  ],
			  "correctAnswer": "AWS"
			}
			
			""";


    //

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";


	@Autowired
    private TestRestTemplate template;

    //
    //[Content-Type:"application/json",


    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "correctAnswer":"AWS"
                }
                """;
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json",responseEntity.getHeaders().get("content-type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
        //assertEquals(expectedResponse.trim(), responseEntity.getBody());
        //System.out.println();
        //System.out.println(responseEntity.getHeaders());
    }



	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {

		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

		String expectedResponse =
				"""
						[
						  {
						    "id": "Question1"
						  },
						  {
						    "id": "Question2"
						  },
						  {
						    "id": "Question3"
						  }
						]
				
				""";

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void addNewSurveyQuestion_basicScenario() {

		String requestBody = """
				{
				  "description": "Your Favorite Language",
				  "options": [
				    "Java",
				    "Python",
				    "JavaScript",
				    "Haskell"
				  ],
				  "correctAnswer": "Java"
				}
			""";


		//

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

		ResponseEntity<String> responseEntity
				= template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

		//DELETE
		//locationHeader
		//REMOVE SIDE EFFECT
		template.delete(locationHeader);

	}



}
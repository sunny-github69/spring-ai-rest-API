package sunny.springframework.RestApiOpenai.controllers;

import sunny.springframework.RestApiOpenai.model.Answer;
import sunny.springframework.RestApiOpenai.model.GetCapitalRequest;
import sunny.springframework.RestApiOpenai.model.Question;
import sunny.springframework.RestApiOpenai.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * Created by jt, Spring Framework Guru.
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        try {
            return openAIService.getAnswer(question);

        } catch (RestClientException e) {
            // Log the error message
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            // Return a meaningful error response
            return new Answer("An error occurred while processing your request.");
        }
    }

}

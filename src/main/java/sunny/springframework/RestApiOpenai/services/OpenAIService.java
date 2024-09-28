package sunny.springframework.RestApiOpenai.services;

import sunny.springframework.RestApiOpenai.model.Answer;
import sunny.springframework.RestApiOpenai.model.GetCapitalRequest;
import sunny.springframework.RestApiOpenai.model.Question;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface OpenAIService {

    Answer getCapital(GetCapitalRequest getCapitalRequest);
    String getAnswer(String question);

    Answer getAnswer(Question question);
}

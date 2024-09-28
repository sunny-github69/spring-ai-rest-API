package sunny.springframework.RestApiOpenai.services;

import sunny.springframework.RestApiOpenai.model.Answer;
import sunny.springframework.RestApiOpenai.model.GetCapitalRequest;
import sunny.springframework.RestApiOpenai.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("I was called");

        try {
            PromptTemplate promptTemplate = new PromptTemplate(question.question());
            Prompt prompt = promptTemplate.create();
            ChatResponse response = chatModel.call(prompt);

            // Check the validity of the response
            if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
                throw new RuntimeException("Invalid response from chat model");
            }

            return new Answer(response.getResult().getOutput().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling chat model", e);
        }
    }

}

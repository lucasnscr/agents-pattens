package com.example.reflectionpattern.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service responsible for generating initial output based on user queries
 */
@Service
public class GeneratorService {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
            You are a helpful AI assistant that generates responses to user queries.
            Provide a detailed and informative initial response to the user's query.
            Focus on being accurate and comprehensive in your response.
            """;

    public GeneratorService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    /**
     * Generate an initial response to the user's query
     * 
     * @param userQuery The user's query
     * @return The generated response
     */
    public String generateInitialOutput(String userQuery) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(userQuery);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
}

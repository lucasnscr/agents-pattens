package com.example.reflectionpattern.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service responsible for reflecting on and improving the initial output
 */
@Service
public class ReflectorService {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
            You are a critical evaluator and improver of AI-generated content.
            Your task is to analyze the provided AI-generated response to a user query,
            identify any issues, inaccuracies, or areas for improvement, and then provide
            a refined and improved version of the response.
            
            Consider the following aspects in your evaluation:
            1. Factual accuracy
            2. Completeness of information
            3. Clarity and coherence
            4. Relevance to the user's query
            5. Potential biases or missing perspectives
            
            First, provide a brief analysis of the strengths and weaknesses of the initial response.
            Then, provide an improved version that addresses the identified issues.
            """;

    public ReflectorService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    /**
     * Reflect on and improve the initial output
     * 
     * @param userQuery The original user query
     * @param initialOutput The initial AI-generated output
     * @return The reflected and improved output
     */
    public String reflectAndImprove(String userQuery, String initialOutput) {
        String reflectionPromptContent = "User Query: " + userQuery + "\n\nInitial AI Response: " + initialOutput;
        
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(reflectionPromptContent);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
    
    /**
     * Iteratively improve the output through multiple reflection cycles
     * 
     * @param userQuery The original user query
     * @param initialOutput The initial AI-generated output
     * @param iterations Number of reflection iterations to perform
     * @return The final improved output after iterations
     */
    public String iterativeReflection(String userQuery, String initialOutput, int iterations) {
        String currentOutput = initialOutput;
        
        for (int i = 0; i < iterations; i++) {
            currentOutput = reflectAndImprove(userQuery, currentOutput);
        }
        
        return currentOutput;
    }
}

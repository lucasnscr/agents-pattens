package com.example.reactpattern.service;

import com.example.reactpattern.tools.SearchTool;
import com.example.reactpattern.tools.EnvironmentTool;
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
 * Service that implements the ReAct pattern (Reasoning + Acting)
 */
@Service
public class ReactService {

    private final ChatClient chatClient;
    private final SearchTool searchTool;
    private final EnvironmentTool environmentTool;
    
    private static final String SYSTEM_PROMPT = """
            You are an AI assistant that follows the ReAct (Reasoning + Acting) pattern to solve problems.
            
            Follow these steps for each user query:
            1. REASON: Think about what information you need and what actions might help solve the problem.
            2. ACT: Use the available tools to gather information or affect the environment.
            3. OBSERVE: Review the results of your actions.
            4. CONTINUE REASONING: Based on your observations, continue reasoning and take further actions if needed.
            
            Available tools:
            - Search tool: Use this to find information on specific topics.
            - Environment tool: Use this to interact with the environment and perform actions.
            
            For each step in your process, clearly indicate whether you are REASONING or ACTING.
            Format your response as follows:
            
            REASONING: [Your thought process about the problem]
            ACTION: [The tool you're using and why]
            OBSERVATION: [Results from the tool]
            REASONING: [Updated thoughts based on the observation]
            ACTION: [Next action if needed]
            ...and so on until you reach a conclusion.
            
            FINAL ANSWER: [Your complete response to the user's query]
            
            Always be thorough in your reasoning and use tools appropriately to gather the information you need.
            """;

    public ReactService(ChatClient.Builder chatClient, SearchTool searchTool, EnvironmentTool environmentTool) {
        this.chatClient = chatClient.build();
        this.searchTool = searchTool;
        this.environmentTool = environmentTool;
    }

    /**
     * Process a user query using the ReAct pattern
     * 
     * @param userQuery The user's query
     * @return The response from the ReAct process
     */
    public String processQuery(String userQuery) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(userQuery);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        
        ChatResponse response = chatClient.prompt(prompt).tools(searchTool, environmentTool)
                .call()
                .chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
}

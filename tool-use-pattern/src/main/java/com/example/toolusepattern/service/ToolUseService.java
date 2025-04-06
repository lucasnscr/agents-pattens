package com.example.toolusepattern.service;

import com.example.toolusepattern.tools.CalendarTool;
import com.example.toolusepattern.tools.WeatherTool;
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
 * Service that coordinates the use of tools by the LLM
 */
@Service
public class ToolUseService {

    private final ChatClient chatClient;
    private final WeatherTool weatherTool;
    private final CalendarTool calendarTool;
    
    private static final String SYSTEM_PROMPT = """
            You are a helpful assistant with access to tools that can provide real-time information.
            When the user asks for information that requires using these tools, use them to provide accurate answers.
            For weather information, use the weather tool.
            For date and calendar information, use the calendar tool.
            If the user's query doesn't require tools, respond directly with your knowledge.
            Always be helpful, concise, and accurate.
            """;

    public ToolUseService(ChatClient.Builder chatClient, WeatherTool weatherTool, CalendarTool calendarTool) {
        this.chatClient = chatClient.build();
        this.weatherTool = weatherTool;
        this.calendarTool = calendarTool;
    }

    /**
     * Process a user query using the appropriate tools
     * 
     * @param userQuery The user's query
     * @return The response from the LLM, potentially using tools
     */
    public String processQuery(String userQuery) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(userQuery);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        
        // Configure the chat client to use our tools
        ChatResponse response = chatClient.prompt(prompt).tools(weatherTool, calendarTool).call().chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
}

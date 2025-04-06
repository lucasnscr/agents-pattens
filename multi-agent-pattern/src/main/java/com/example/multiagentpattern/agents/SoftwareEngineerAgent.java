package com.example.multiagentpattern.agents;

import com.example.multiagentpattern.model.Task;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Software Engineer agent responsible for implementation and development tasks
 */
@Component
public class SoftwareEngineerAgent {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
            You are a skilled Software Engineer AI agent.
            Your responsibilities include:
            1. Implementing features and functionality
            2. Writing clean, maintainable code
            3. Unit and integration testing
            4. Debugging and troubleshooting
            5. Documentation
            6. Performance optimization
            
            Provide detailed, well-structured code solutions for development tasks.
            Include code examples, explanations, and testing strategies when appropriate.
            """;

    public SoftwareEngineerAgent(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Execute a software development task
     * 
     * @param task The task to execute
     * @return The result of the task execution
     */
    public String executeTask(Task task) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(
            "Please execute the following software development task:\n\n" + 
            "Task: " + task.getDescription() + 
            "\n\nProvide a detailed solution with code examples, implementation details, and testing strategies."
        );
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
}

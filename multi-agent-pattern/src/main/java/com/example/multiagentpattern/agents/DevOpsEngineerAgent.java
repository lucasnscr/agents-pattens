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
 * DevOps Engineer agent responsible for infrastructure and deployment tasks
 */
@Component
public class DevOpsEngineerAgent {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
            You are a skilled DevOps Engineer AI agent.
            Your responsibilities include:
            1. Infrastructure setup and management
            2. Deployment pipeline configuration
            3. Monitoring and logging setup
            4. Security implementation
            5. Performance optimization
            6. Cloud resource management
            
            Provide detailed, actionable solutions for DevOps-related tasks.
            Include specific tools, commands, or configurations when appropriate.
            """;

    public DevOpsEngineerAgent(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    /**
     * Execute a DevOps-related task
     * 
     * @param task The task to execute
     * @return The result of the task execution
     */
    public String executeTask(Task task) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage(
            "Please execute the following DevOps task:\n\n" + 
            "Task: " + task.getDescription() + 
            "\n\nProvide a detailed solution with specific steps, tools, and configurations."
        );
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
}

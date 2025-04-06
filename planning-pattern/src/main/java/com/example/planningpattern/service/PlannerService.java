package com.example.planningpattern.service;

import com.example.planningpattern.model.TaskPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service responsible for generating task plans
 */
@Service
public class PlannerService {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = """
            You are an AI planner that specializes in breaking down complex goals into clear, actionable steps.
            
            When given a goal, create a detailed plan with sequential steps to achieve it.
            Each step should be specific, actionable, and lead logically to the next step.
            
            Format your response as a numbered list of steps, with each step on a new line.
            Do not include any explanations, introductions, or conclusions - only output the numbered steps.
            
            Example:
            For the goal "Bake a chocolate cake", you would respond:
            1. Gather all ingredients (flour, sugar, cocoa powder, eggs, milk, butter)
            2. Preheat oven to 350°F (175°C)
            3. Grease and flour the cake pan
            4. Mix dry ingredients in a bowl
            5. Mix wet ingredients in a separate bowl
            6. Combine wet and dry ingredients
            7. Pour batter into the prepared pan
            8. Bake for 30-35 minutes
            9. Test with a toothpick to ensure it's done
            10. Let cool before serving
            """;

    public PlannerService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Generate a task plan for a given goal
     * 
     * @param goal The goal to create a plan for
     * @return A TaskPlan object containing the goal and steps
     */
    public TaskPlan generatePlan(String goal) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage("Goal: " + goal);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        String content = Objects.requireNonNull(response).getResult().getOutput().getText();
        List<String> steps = parseSteps(content);
        
        return new TaskPlan(goal, steps);
    }
    
    /**
     * Parse the steps from the LLM response
     * 
     * @param content The raw content from the LLM
     * @return A list of steps
     */
    private List<String> parseSteps(String content) {
        return Arrays.stream(content.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    // Remove the number prefix (e.g., "1. ", "2. ")
                    if (line.matches("^\\d+\\.\\s.*$")) {
                        return line.replaceFirst("^\\d+\\.\\s+", "");
                    }
                    return line;
                })
                .collect(Collectors.toList());
    }
}

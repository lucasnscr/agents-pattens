package com.example.planningpattern.service;

import com.example.planningpattern.model.TaskPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service responsible for executing task plans
 */
@Service
public class TaskExecutionService {

    private final ChatClient chatClient;
    private final Map<String, TaskPlan> activePlans;
    
    private static final String SYSTEM_PROMPT = """
            You are an AI task executor that specializes in carrying out specific tasks.
            
            You will be given a task to execute. Your job is to:
            1. Determine if the task can be executed
            2. If it can be executed, describe how you would execute it and what the outcome would be
            3. If it cannot be executed, explain why
            
            Be specific and detailed in your response. Focus only on the execution of the given task.
            """;

    public TaskExecutionService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
        this.activePlans = new HashMap<>();
    }
    
    /**
     * Store a task plan for later execution
     * 
     * @param plan The task plan to store
     */
    public void storePlan(TaskPlan plan) {
        activePlans.put(plan.getGoal(), plan);
    }
    
    /**
     * Get a stored task plan by goal
     * 
     * @param goal The goal of the task plan
     * @return The task plan, or null if not found
     */
    public TaskPlan getPlan(String goal) {
        return activePlans.get(goal);
    }
    
    /**
     * Get all stored task plans
     * 
     * @return A map of all stored task plans
     */
    public Map<String, TaskPlan> getAllPlans() {
        return new HashMap<>(activePlans);
    }
    
    /**
     * Execute a single task from a plan
     * 
     * @param task The task to execute
     * @return The execution result
     */
    public String executeSingleTask(String task) {
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage();
        Message userMessage = new UserMessage("Task to execute: " + task);
        
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        
        return Objects.requireNonNull(response).getResult().getOutput().getText();
    }
    
    /**
     * Execute all steps in a task plan
     * 
     * @param goal The goal of the task plan to execute
     * @return The execution results for each step
     */
    public List<String> executeFullPlan(String goal) {
        TaskPlan plan = activePlans.get(goal);
        if (plan == null) {
            throw new IllegalArgumentException("No plan found for goal: " + goal);
        }
        
        List<String> results = plan.getSteps().stream()
                .map(this::executeSingleTask)
                .toList();
        
        plan.setCompleted(true);
        return results;
    }
}

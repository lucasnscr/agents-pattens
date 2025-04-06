package com.example.multiagentpattern.service;

import com.example.multiagentpattern.agents.DevOpsEngineerAgent;
import com.example.multiagentpattern.agents.ProjectManagerAgent;
import com.example.multiagentpattern.agents.SoftwareEngineerAgent;
import com.example.multiagentpattern.agents.TechnicalLeadAgent;
import com.example.multiagentpattern.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service that orchestrates the multi-agent system
 */
@Service
public class MultiAgentOrchestrator {

    private final ProjectManagerAgent projectManagerAgent;
    private final DevOpsEngineerAgent devOpsEngineerAgent;
    private final TechnicalLeadAgent technicalLeadAgent;
    private final SoftwareEngineerAgent softwareEngineerAgent;
    
    private final Map<String, Task> tasks;
    private final Map<String, List<Task>> projectTasks;

    public MultiAgentOrchestrator(
            ProjectManagerAgent projectManagerAgent,
            DevOpsEngineerAgent devOpsEngineerAgent,
            TechnicalLeadAgent technicalLeadAgent,
            SoftwareEngineerAgent softwareEngineerAgent) {
        this.projectManagerAgent = projectManagerAgent;
        this.devOpsEngineerAgent = devOpsEngineerAgent;
        this.technicalLeadAgent = technicalLeadAgent;
        this.softwareEngineerAgent = softwareEngineerAgent;
        
        this.tasks = new HashMap<>();
        this.projectTasks = new HashMap<>();
    }

    /**
     * Process a project request through the multi-agent system
     * 
     * @param projectId A unique identifier for the project
     * @param projectRequest The project request to process
     * @return A list of tasks created for the project
     */
    public List<Task> processProjectRequest(String projectId, String projectRequest) {
        // Step 1: Project Manager analyzes the request and breaks it down into tasks
        List<String> taskDescriptions = projectManagerAgent.analyzeProjectRequest(projectRequest);
        
        // Step 2: Create task objects and store them
        List<Task> projectTaskList = new ArrayList<>();
        for (String description : taskDescriptions) {
            String taskId = UUID.randomUUID().toString();
            Task task = new Task(taskId, description, "UNKNOWN");
            tasks.put(taskId, task);
            projectTaskList.add(task);
        }
        
        // Store the tasks for this project
        projectTasks.put(projectId, projectTaskList);
        
        // Step 3: Project Manager delegates each task to the appropriate specialist
        for (Task task : projectTaskList) {
            String specialist = projectManagerAgent.delegateTask(task);
            task.setAssignedAgent(specialist);
            task.setStatus("ASSIGNED");
        }
        
        return projectTaskList;
    }
    
    /**
     * Execute a specific task
     * 
     * @param taskId The ID of the task to execute
     * @return The result of the task execution
     */
    public String executeTask(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        
        if (!"ASSIGNED".equals(task.getStatus())) {
            throw new IllegalStateException("Task is not in ASSIGNED state: " + taskId);
        }
        
        String result = switch (task.getAssignedAgent()) {
            case "DevOps Engineer" -> devOpsEngineerAgent.executeTask(task);
            case "Technical Lead" -> technicalLeadAgent.executeTask(task);
            case "Software Engineer" -> softwareEngineerAgent.executeTask(task);
            default -> throw new IllegalStateException("Unknown agent type: " + task.getAssignedAgent());
        };

        task.setResult(result);
        task.setStatus("COMPLETED");
        
        return result;
    }
    
    /**
     * Execute all tasks for a project
     * 
     * @param projectId The ID of the project
     * @return A map of task IDs to execution results
     */
    public Map<String, String> executeAllProjectTasks(String projectId) {
        List<Task> projectTaskList = projectTasks.get(projectId);
        if (projectTaskList == null) {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
        
        Map<String, String> results = new HashMap<>();
        for (Task task : projectTaskList) {
            String result = executeTask(task.getId());
            results.put(task.getId(), result);
        }
        
        return results;
    }
    
    /**
     * Get all tasks for a project
     * 
     * @param projectId The ID of the project
     * @return A list of tasks for the project
     */
    public List<Task> getProjectTasks(String projectId) {
        List<Task> projectTaskList = projectTasks.get(projectId);
        if (projectTaskList == null) {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
        
        return new ArrayList<>(projectTaskList);
    }
    
    /**
     * Get a specific task
     * 
     * @param taskId The ID of the task
     * @return The task
     */
    public Task getTask(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        
        return task;
    }
}

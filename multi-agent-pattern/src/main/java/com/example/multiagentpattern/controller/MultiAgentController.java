package com.example.multiagentpattern.controller;

import com.example.multiagentpattern.model.Task;
import com.example.multiagentpattern.service.MultiAgentOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controller for the Multi-agent Pattern API
 */
@RestController
@RequestMapping("/api/multi-agent")
public class MultiAgentController {

    private final MultiAgentOrchestrator orchestrator;

    public MultiAgentController(MultiAgentOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    /**
     * Process a project request through the multi-agent system
     * 
     * @param projectRequest The project request to process
     * @return A list of tasks created for the project
     */
    @PostMapping("/projects")
    public ResponseEntity<Map<String, Object>> createProject(@RequestParam String projectRequest) {
        String projectId = UUID.randomUUID().toString();
        List<Task> tasks = orchestrator.processProjectRequest(projectId, projectRequest);
        
        return ResponseEntity.ok(Map.of(
            "projectId", projectId,
            "tasks", tasks
        ));
    }
    
    /**
     * Get all tasks for a project
     * 
     * @param projectId The ID of the project
     * @return A list of tasks for the project
     */
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable String projectId) {
        List<Task> tasks = orchestrator.getProjectTasks(projectId);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Execute a specific task
     * 
     * @param taskId The ID of the task to execute
     * @return The result of the task execution
     */
    @PostMapping("/tasks/{taskId}/execute")
    public ResponseEntity<Map<String, Object>> executeTask(@PathVariable String taskId) {
        String result = orchestrator.executeTask(taskId);
        Task task = orchestrator.getTask(taskId);
        
        return ResponseEntity.ok(Map.of(
            "taskId", taskId,
            "description", task.getDescription(),
            "assignedAgent", task.getAssignedAgent(),
            "status", task.getStatus(),
            "result", result
        ));
    }
    
    /**
     * Execute all tasks for a project
     * 
     * @param projectId The ID of the project
     * @return A map of task IDs to execution results
     */
    @PostMapping("/projects/{projectId}/execute-all")
    public ResponseEntity<Map<String, Object>> executeAllProjectTasks(@PathVariable String projectId) {
        Map<String, String> results = orchestrator.executeAllProjectTasks(projectId);
        List<Task> tasks = orchestrator.getProjectTasks(projectId);
        
        return ResponseEntity.ok(Map.of(
            "projectId", projectId,
            "tasks", tasks,
            "results", results
        ));
    }
    
    /**
     * Get a specific task
     * 
     * @param taskId The ID of the task
     * @return The task
     */
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String taskId) {
        Task task = orchestrator.getTask(taskId);
        return ResponseEntity.ok(task);
    }
}

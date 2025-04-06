package com.example.planningpattern.controller;

import com.example.planningpattern.model.TaskPlan;
import com.example.planningpattern.service.PlannerService;
import com.example.planningpattern.service.TaskExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Planning Pattern API
 */
@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    private final PlannerService plannerService;
    private final TaskExecutionService taskExecutionService;

    public PlanningController(PlannerService plannerService, TaskExecutionService taskExecutionService) {
        this.plannerService = plannerService;
        this.taskExecutionService = taskExecutionService;
    }

    /**
     * Generate a task plan for a given goal
     * 
     * @param goal The goal to create a plan for
     * @return The generated task plan
     */
    @PostMapping("/plan")
    public ResponseEntity<TaskPlan> generatePlan(@RequestParam String goal) {
        TaskPlan plan = plannerService.generatePlan(goal);
        taskExecutionService.storePlan(plan);
        return ResponseEntity.ok(plan);
    }
    
    /**
     * Get all stored task plans
     * 
     * @return A map of all stored task plans
     */
    @GetMapping("/plans")
    public ResponseEntity<Map<String, TaskPlan>> getAllPlans() {
        return ResponseEntity.ok(taskExecutionService.getAllPlans());
    }
    
    /**
     * Get a specific task plan by goal
     * 
     * @param goal The goal of the task plan
     * @return The task plan, or 404 if not found
     */
    @GetMapping("/plan")
    public ResponseEntity<TaskPlan> getPlan(@RequestParam String goal) {
        TaskPlan plan = taskExecutionService.getPlan(goal);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan);
    }
    
    /**
     * Execute a single task
     * 
     * @param task The task to execute
     * @return The execution result
     */
    @PostMapping("/execute-task")
    public ResponseEntity<String> executeSingleTask(@RequestParam String task) {
        String result = taskExecutionService.executeSingleTask(task);
        return ResponseEntity.ok(result);
    }
    
    /**
     * Execute all steps in a task plan
     * 
     * @param goal The goal of the task plan to execute
     * @return The execution results for each step
     */
    @PostMapping("/execute-plan")
    public ResponseEntity<Map<String, Object>> executeFullPlan(@RequestParam String goal) {
        TaskPlan plan = taskExecutionService.getPlan(goal);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<String> results = taskExecutionService.executeFullPlan(goal);
        
        Map<String, Object> response = new HashMap<>();
        response.put("goal", goal);
        response.put("steps", plan.getSteps());
        response.put("results", results);
        response.put("completed", plan.isCompleted());
        
        return ResponseEntity.ok(response);
    }
}

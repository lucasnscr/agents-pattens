package com.example.reactpattern.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Environment tool that provides interaction with a simulated environment
 */
@Component
public class EnvironmentTool {

    // Simulated environment state
    private final Map<String, Object> environmentState;
    private final List<String> actionHistory;
    
    public EnvironmentTool() {
        // Initialize environment state
        environmentState = new HashMap<>();
        environmentState.put("location", "home");
        environmentState.put("inventory", new ArrayList<String>());
        environmentState.put("weather", "sunny");
        environmentState.put("time", "morning");
        
        actionHistory = new ArrayList<>();
    }

    /**
     * Get the current state of the environment
     * 
     * @return Environment state as a formatted string
     */
    @Tool(description = "Get the current state of the environment")
    public String getEnvironmentState() {
        StringBuilder state = new StringBuilder("Current Environment State:\n");
        
        state.append("Location: ").append(environmentState.get("location")).append("\n");
        state.append("Weather: ").append(environmentState.get("weather")).append("\n");
        state.append("Time: ").append(environmentState.get("time")).append("\n");
        
        @SuppressWarnings("unchecked")
        List<String> inventory = (List<String>) environmentState.get("inventory");
        state.append("Inventory: ");
        if (inventory.isEmpty()) {
            state.append("empty");
        } else {
            state.append(String.join(", ", inventory));
        }
        
        return state.toString();
    }
    
    /**
     * Perform an action in the environment
     * 
     * @param action The action to perform
     * @return Result of the action as a formatted string
     */
    @Tool(description = "Perform an action in the environment (e.g., 'move to park', 'pick up book', 'check weather')")
    public String performAction(String action) {
        String normalizedAction = action.toLowerCase().trim();
        actionHistory.add(normalizedAction);
        
        // Handle movement actions
        if (normalizedAction.startsWith("move to ") || normalizedAction.startsWith("go to ")) {
            String newLocation = normalizedAction.replace("move to ", "").replace("go to ", "");
            String oldLocation = (String) environmentState.get("location");
            environmentState.put("location", newLocation);
            return "Moved from " + oldLocation + " to " + newLocation + ".";
        }
        
        // Handle inventory actions
        if (normalizedAction.startsWith("pick up ") || normalizedAction.startsWith("take ")) {
            String item = normalizedAction.replace("pick up ", "").replace("take ", "");
            @SuppressWarnings("unchecked")
            List<String> inventory = (List<String>) environmentState.get("inventory");
            inventory.add(item);
            return "Added " + item + " to inventory.";
        }
        
        if (normalizedAction.startsWith("drop ")) {
            String item = normalizedAction.replace("drop ", "");
            @SuppressWarnings("unchecked")
            List<String> inventory = (List<String>) environmentState.get("inventory");
            if (inventory.contains(item)) {
                inventory.remove(item);
                return "Removed " + item + " from inventory.";
            } else {
                return "You don't have " + item + " in your inventory.";
            }
        }
        
        // Handle weather checking
        if (normalizedAction.contains("weather")) {
            return "The current weather is " + environmentState.get("weather") + ".";
        }
        
        // Handle time checking
        if (normalizedAction.contains("time")) {
            return "The current time is " + environmentState.get("time") + ".";
        }
        
        // Default response for unrecognized actions
        return "Action performed: " + action + ". No specific outcome.";
    }
    
    /**
     * Get the history of actions performed
     * 
     * @return Action history as a formatted string
     */
    @Tool(description = "Get the history of actions performed in the environment")
    public String getActionHistory() {
        if (actionHistory.isEmpty()) {
            return "No actions have been performed yet.";
        }
        
        StringBuilder history = new StringBuilder("Action History:\n");
        for (int i = 0; i < actionHistory.size(); i++) {
            history.append(i + 1).append(". ").append(actionHistory.get(i)).append("\n");
        }
        
        return history.toString();
    }
}

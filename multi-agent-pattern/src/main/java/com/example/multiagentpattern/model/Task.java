package com.example.multiagentpattern.model;

/**
 * Represents a task that can be delegated to different agents
 */
public class Task {
    private final String id;
    private final String description;
    private final String type;
    private String status;
    private String result;
    private String assignedAgent;

    public Task(String id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.status = "PENDING";
        this.result = null;
        this.assignedAgent = null;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(String assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", assignedAgent='" + assignedAgent + '\'' +
                '}';
    }
}

# Multi-agent Pattern Implementation with Spring AI

## Overview

The Multi-agent Pattern is an advanced AI agent design pattern that enables collaboration between specialized AI agents to solve complex problems. This implementation demonstrates how to build a multi-agent system using Spring AI where different agents with distinct roles work together under the coordination of a project manager agent.

## Problem Solved

The Multi-agent Pattern addresses several key challenges in AI systems:

1. **Complex Problem Domains**: Problems that require expertise across multiple domains.
2. **Specialization vs. Generalization**: The trade-off between deep expertise in specific areas and broad knowledge.
3. **Task Delegation**: Efficiently assigning tasks to the most appropriate agent.
4. **Coordination Overhead**: Managing communication and dependencies between agents.
5. **Scalability**: Handling growing complexity by adding specialized agents rather than creating more complex individual agents.

## How It Works

The Multi-agent Pattern works through a hierarchical collaboration process:

1. **Project Request**: A user submits a project request that requires multiple specialized skills.
2. **Task Decomposition**: A Project Manager agent analyzes the request and breaks it down into specific tasks.
3. **Task Delegation**: The Project Manager assigns each task to the most appropriate specialist agent.
4. **Parallel Execution**: Specialist agents work on their assigned tasks independently.
5. **Result Aggregation**: Results from all agents are collected and combined.
6. **Final Response**: The complete solution is returned to the user.

## Implementation Details

This implementation uses Spring AI with the following components:

- **Task**: Model class representing a task that can be assigned to different agents.
- **Specialized Agents**: Different agent classes with specific expertise:
  - **ProjectManagerAgent**: Responsible for task decomposition and delegation.
  - **DevOpsEngineerAgent**: Handles infrastructure, deployment, and operations tasks.
  - **TechnicalLeadAgent**: Manages architecture, technical decisions, and code reviews.
  - **SoftwareEngineerAgent**: Implements features and functionality.
- **MultiAgentOrchestrator**: Service that coordinates the interactions between agents.
- **MultiAgentController**: REST API endpoints that expose the multi-agent functionality.

## Use Cases

The Multi-agent Pattern is particularly useful for:

- Software development projects requiring multiple specialized roles
- Complex business processes spanning different departments
- Research projects requiring diverse expertise
- Customer service systems that need to route queries to specialists
- Any scenario where different types of expertise need to be combined

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- OpenAI API key

### Configuration

Set your OpenAI API key in the application.properties file or as an environment variable:

```
export OPENAI_API_KEY=your-api-key
```

### Running the Application

```bash
cd multi-agent-pattern
mvn spring-boot:run
```

### API Usage

The Multi-agent Pattern API provides several endpoints:

```
POST /api/multi-agent/projects?projectRequest=your-project-request
```
Creates a new project, breaks it down into tasks, and assigns them to appropriate specialists.

```
GET /api/multi-agent/projects/{projectId}/tasks
```
Retrieves all tasks for a specific project.

```
POST /api/multi-agent/tasks/{taskId}/execute
```
Executes a specific task using the assigned specialist agent.

```
POST /api/multi-agent/projects/{projectId}/execute-all
```
Executes all tasks for a project and returns the results.

```
GET /api/multi-agent/tasks/{taskId}
```
Retrieves information about a specific task.

Example project requests:
- "Build a microservice-based e-commerce platform with user authentication, product catalog, and payment processing"
- "Create a CI/CD pipeline for a Java application with automated testing and deployment to AWS"
- "Develop a real-time analytics dashboard for monitoring system performance"

## Benefits of the Multi-agent Pattern

- **Specialized Expertise**: Each agent can excel in its specific domain.
- **Scalability**: New specialist agents can be added without changing the core system.
- **Parallel Processing**: Multiple agents can work simultaneously on different tasks.
- **Flexibility**: The system can handle a wide range of problems by combining different specialists.
- **Maintainability**: Each agent can be updated or replaced independently.
- **Resource Efficiency**: Specialized agents can be optimized for their specific tasks.

## Limitations

- **Coordination Complexity**: Managing communication between multiple agents adds overhead.
- **Potential Bottlenecks**: The project manager agent can become a bottleneck.
- **Integration Challenges**: Ensuring consistent output formats and quality across different agents.
- **Increased Latency**: Communication between agents adds time to the overall process.
- **Resource Intensive**: Running multiple LLM instances requires more computational resources.

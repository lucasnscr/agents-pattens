# Planning Pattern Implementation with Spring AI

## Overview

The Planning Pattern is a sophisticated AI agent design pattern that enables complex task decomposition and execution. This implementation demonstrates how to build a planning-based system using Spring AI that can break down high-level goals into actionable steps and execute them systematically.

## Problem Solved

The Planning Pattern addresses several key challenges in AI systems:

1. **Complex Task Management**: Breaking down complex goals into manageable, sequential steps.
2. **Structured Execution**: Providing a clear execution path for multi-step processes.
3. **Progress Tracking**: Monitoring the completion status of tasks and subtasks.
4. **Adaptability to Failure**: Allowing for re-planning when steps cannot be completed.
5. **Coordination of Multiple Actions**: Managing dependencies between different tasks.

## How It Works

The Planning Pattern works through a multi-step process:

1. **Goal Definition**: The user defines a high-level goal or objective.
2. **Task Planning**: A Planner component analyzes the goal and breaks it down into a sequence of specific, actionable steps.
3. **Plan Validation**: The generated plan is checked for completeness and feasibility.
4. **Task Execution**: Each step in the plan is executed sequentially by a ReAct Agent.
5. **Progress Monitoring**: The system tracks which steps have been completed and which remain.
6. **Completion Verification**: The system checks if the overall goal has been achieved.
7. **Re-Planning (if necessary)**: If certain steps fail, the system can generate a new plan.

## Implementation Details

This implementation uses Spring AI with the following components:

- **TaskPlan**: Model class representing a plan with a goal and a list of steps.
- **PlannerService**: Responsible for generating task plans by breaking down goals into steps.
- **TaskExecutionService**: Manages the execution of individual tasks and full plans.
- **PlanningController**: REST API endpoints that expose planning and execution functionality.

## Use Cases

The Planning Pattern is particularly useful for:

- Complex project management
- Multi-step workflows
- Task automation systems
- Intelligent assistants that handle complex requests
- Systems that need to coordinate multiple actions
- Any scenario requiring structured approach to problem-solving

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
cd planning-pattern
mvn spring-boot:run
```

### API Usage

The Planning Pattern API provides several endpoints:

```
POST /api/planning/plan?goal=your-goal
```
Generates a task plan for the specified goal.

```
GET /api/planning/plans
```
Retrieves all stored task plans.

```
GET /api/planning/plan?goal=your-goal
```
Retrieves a specific task plan by goal.

```
POST /api/planning/execute-task?task=your-task
```
Executes a single task and returns the result.

```
POST /api/planning/execute-plan?goal=your-goal
```
Executes all steps in a task plan and returns the results.

Example goals:
- "Plan a weekend trip to New York City"
- "Create a marketing strategy for a new product"
- "Organize a team-building event"
- "Develop a personal fitness routine"

## Benefits of the Planning Pattern

- **Structured Approach**: Provides a clear, step-by-step path to achieving complex goals.
- **Modularity**: Each step can be executed independently, allowing for flexible implementation.
- **Visibility**: The entire plan is visible and can be reviewed before execution.
- **Progress Tracking**: Easy to monitor which steps have been completed and which remain.
- **Adaptability**: Plans can be modified or regenerated if circumstances change.
- **Scalability**: Can handle goals of varying complexity by adjusting the granularity of steps.

## Limitations

- **Upfront Planning Overhead**: Requires time to generate a complete plan before execution begins.
- **Rigidity**: May struggle with highly dynamic environments where conditions change rapidly.
- **Dependency Handling**: Complex dependencies between steps can be challenging to manage.
- **Error Recovery**: If a step fails, the entire plan may need to be regenerated.
- **Quality Dependence**: The effectiveness depends on the quality of the initial plan generation.

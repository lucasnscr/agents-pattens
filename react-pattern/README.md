# ReAct Pattern Implementation with Spring AI

## Overview

The ReAct Pattern (Reasoning + Acting) is a powerful AI agent design pattern that combines reasoning with action. This implementation demonstrates how to build a ReAct-based system using Spring AI that can reason about problems, take actions to gather information or affect the environment, observe the results, and continue reasoning based on those observations.

## Problem Solved

The ReAct Pattern addresses several key challenges in AI systems:

1. **Limited Problem-Solving Capabilities**: Traditional LLMs can reason but cannot take actions to gather additional information.
2. **Lack of Adaptability**: Systems without action capabilities cannot adjust their approach based on new information.
3. **Incomplete Information**: Many problems require gathering information dynamically to reach a solution.
4. **Complex Multi-Step Tasks**: Some tasks require a sequence of reasoning and action steps to complete.

## How It Works

The ReAct Pattern works through a multi-step iterative process:

1. **Reasoning**: The AI analyzes the problem and determines what information it needs or what actions might help solve it.
2. **Acting**: Based on its reasoning, the AI uses available tools to gather information or affect the environment.
3. **Observing**: The AI reviews the results of its actions.
4. **Continued Reasoning**: Based on observations, the AI updates its understanding and determines next steps.
5. **Iteration**: The process repeats until the AI has enough information to provide a complete answer.

## Implementation Details

This implementation uses Spring AI with the following components:

- **Tool Classes**: Java classes with methods annotated with `@Tool` that provide specific functionality.
  - `SearchTool`: Provides information retrieval capabilities from a knowledge base.
  - `EnvironmentTool`: Allows interaction with a simulated environment (movement, inventory management, etc.).
- **ReactService**: Coordinates the reasoning and action process using the ReAct pattern.
- **ReactController**: REST API endpoint that exposes the ReAct functionality.

## Use Cases

The ReAct Pattern is particularly useful for:

- Complex problem-solving requiring multiple steps
- Information gathering and research tasks
- Virtual assistants that need to interact with the environment
- Task automation systems
- Game agents and interactive simulations
- Any scenario requiring both reasoning and action capabilities

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
cd react-pattern
mvn spring-boot:run
```

### API Usage

To use the ReAct pattern API:

```
GET /api/react?query=your-query
```

Example queries:
- "What is the ReAct pattern and how does it work?"
- "I want to go to the park and then check the weather"
- "Find information about large language models and then move to the library"
- "What items do I have in my inventory?"

## Benefits of the ReAct Pattern

- **Enhanced Problem-Solving**: Combines the reasoning capabilities of LLMs with the ability to take actions.
- **Dynamic Information Gathering**: Can collect information as needed during the problem-solving process.
- **Transparency**: The step-by-step reasoning and action process is visible and explainable.
- **Adaptability**: Can adjust its approach based on new information gathered during the process.
- **Reduced Hallucinations**: Grounding in real actions and observations reduces the likelihood of fabricated information.

## Limitations

- **Complexity**: The ReAct pattern is more complex to implement than simpler patterns.
- **Increased Latency**: The iterative process of reasoning and acting increases response time.
- **Tool Dependency**: The effectiveness is limited by the available tools and their capabilities.
- **Prompt Engineering Challenges**: Crafting effective prompts for the ReAct pattern requires careful design.

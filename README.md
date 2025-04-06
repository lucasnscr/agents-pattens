# Spring AI Agent Patterns

This repository contains implementations of the 5 most popular Agentic AI design patterns using the Spring AI Library. Each pattern is implemented as a separate Spring Boot application with detailed documentation.

## Overview

Agentic AI design patterns provide structured approaches to building AI systems that can reason, act, and interact with their environment. This repository demonstrates how to implement these patterns using Spring AI, a Spring-friendly API for developing AI applications.

## Patterns Implemented

### 1. Reflection Pattern

The Reflection Pattern enables self-improvement through iterative evaluation and refinement. It allows an AI system to generate an initial response, critically evaluate it, and then produce an improved version.

[View Reflection Pattern Implementation](./reflection-pattern/)

### 2. Tool Use Pattern

The Tool Use Pattern enables AI systems to interact with external tools and APIs to access real-time information and perform actions. This pattern extends the capabilities of LLMs beyond their training data.

[View Tool Use Pattern Implementation](./tool-use-pattern/)

### 3. ReAct Pattern

The ReAct Pattern (Reasoning + Acting) combines reasoning with action capabilities. It enables AI systems to reason about problems, take actions to gather information or affect the environment, observe the results, and continue reasoning based on those observations.

[View ReAct Pattern Implementation](./react-pattern/)

### 4. Planning Pattern

The Planning Pattern enables complex task decomposition and execution. It allows an AI system to break down high-level goals into actionable steps and execute them systematically.

[View Planning Pattern Implementation](./planning-pattern/)

### 5. Multi-agent Pattern

The Multi-agent Pattern enables collaboration between specialized AI agents to solve complex problems. It involves multiple agents with distinct roles working together under the coordination of a project manager agent.

[View Multi-agent Pattern Implementation](./multi-agent-pattern/)

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- OpenAI API key

### Configuration

Each pattern implementation requires an OpenAI API key. Set it in the application.properties file or as an environment variable:

```
export OPENAI_API_KEY=your-api-key
```

### Running the Applications

To run any of the pattern implementations:

```bash
cd [pattern-directory]
mvn spring-boot:run
```

## Pattern Comparison

| Pattern | Key Benefit | Best Use Case | Implementation Complexity |
|---------|-------------|---------------|---------------------------|
| Reflection | Self-improvement | Content generation requiring high quality | Medium |
| Tool Use | External capabilities | Real-time information access | Medium |
| ReAct | Reasoning with action | Complex problem-solving | High |
| Planning | Structured execution | Multi-step processes | High |
| Multi-agent | Specialized expertise | Cross-domain problems | Very High |

## References

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring AI GitHub Repository](https://github.com/spring-projects/spring-ai)
- [Spring AI Examples](https://github.com/spring-projects/spring-ai-examples)

## License

This project is licensed under the MIT License - see the LICENSE file for details.

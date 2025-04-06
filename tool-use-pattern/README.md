# Tool Use Pattern Implementation with Spring AI

## Overview

The Tool Use Pattern is a powerful AI agent design pattern that enables AI systems to interact with external tools and APIs to access real-time information and perform actions. This implementation demonstrates how to build a tool-enabled system using Spring AI that can dynamically call tools based on user queries.

## Problem Solved

The Tool Use Pattern addresses several key challenges in AI systems:

1. **Limited Knowledge**: LLMs have knowledge cutoffs and lack access to real-time information.
2. **Inability to Perform Actions**: Traditional LLMs cannot interact with external systems or APIs.
3. **Factual Accuracy**: Without access to external data sources, LLMs may provide outdated or incorrect information.
4. **Specialized Functionality**: Some tasks require specialized tools or calculations that LLMs cannot perform internally.

## How It Works

The Tool Use Pattern works through a multi-step process:

1. **User Query**: The user submits a query that may require external information or actions.
2. **LLM Analysis**: The LLM analyzes the query to determine if tools are needed.
3. **Tool Selection**: If tools are needed, the LLM selects the appropriate tool and formats the necessary parameters.
4. **Tool Execution**: The system executes the selected tool with the provided parameters.
5. **Result Integration**: The tool's results are returned to the LLM, which incorporates them into its response.
6. **Final Response**: The LLM generates a final response that includes the information obtained from the tools.

## Implementation Details

This implementation uses Spring AI with the following components:

- **Tool Classes**: Java classes with methods annotated with `@Tool` that provide specific functionality.
  - `WeatherTool`: Provides weather information for different locations.
  - `CalendarTool`: Provides date-related information and scheduling capabilities.
- **ToolUseService**: Coordinates the interaction between the LLM and the tools.
- **ToolUseController**: REST API endpoint that exposes the tool use functionality.

## Use Cases

The Tool Use Pattern is particularly useful for:

- Virtual assistants that need access to real-time information
- Systems that need to perform actions on behalf of users
- Applications requiring integration with external APIs
- Chatbots that need to provide accurate, up-to-date information
- Any scenario where the LLM needs capabilities beyond its training data

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- OpenAI API key

### Configuration

Set your OpenAI API key in the application.properties file or as an environment variable:

```
export OPENAI_API_KEY=your-api-key
```

### Running the Application

```bash
cd tool-use-pattern
mvn spring-boot:run
```

### API Usage

To use the tool use pattern API:

```
GET /api/tool-use?query=your-query
```

Example queries:
- "What's the weather like in New York?"
- "What's today's date?"
- "Check my calendar for events today"
- "Schedule a meeting tomorrow at 3 PM"

## Benefits of the Tool Use Pattern

- **Enhanced Capabilities**: Extends the LLM's abilities beyond its training data.
- **Real-time Information**: Provides access to current data that may not be in the LLM's knowledge.
- **Action Execution**: Enables the LLM to perform actions in external systems.
- **Improved Accuracy**: Reduces hallucinations by grounding responses in factual, current data.
- **Modularity**: New tools can be added without changing the core system.

## Limitations

- **Tool Definition Complexity**: Tools must be carefully defined with clear descriptions.
- **Parameter Formatting**: The LLM may sometimes format tool parameters incorrectly.
- **Increased Latency**: Tool execution adds additional processing time.
- **Security Considerations**: Tool access must be carefully controlled to prevent misuse.

package com.example.reactpattern.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Search tool that provides information retrieval capabilities
 */
@Component
public class SearchTool {

    // Simulated knowledge base
    private final Map<String, String> knowledgeBase;
    
    public SearchTool() {
        // Initialize with some sample data
        knowledgeBase = new HashMap<>();
        
        knowledgeBase.put("spring ai", 
            "Spring AI is an application framework for AI engineering. It provides a Spring-friendly API and abstractions " +
            "for developing AI applications. It supports various AI models including OpenAI, Azure OpenAI, Anthropic, and more.");
        
        knowledgeBase.put("react pattern", 
            "The ReAct pattern (Reasoning + Acting) is an AI agent design pattern that combines reasoning with action. " +
            "It enables AI systems to reason about a problem, take actions to gather information or affect the environment, " +
            "observe the results, and continue reasoning based on those observations.");
        
        knowledgeBase.put("large language models", 
            "Large Language Models (LLMs) are advanced AI systems trained on vast amounts of text data. " +
            "They can generate human-like text, translate languages, write different kinds of creative content, " +
            "and answer questions in an informative way. Examples include GPT-4, Claude, and Llama.");
    }

    /**
     * Search for information on a specific topic
     * 
     * @param query The search query
     * @return Search results as a formatted string
     */
    @Tool(description = "Search for information on a specific topic")
    public String search(String query) {
        String normalizedQuery = query.toLowerCase().trim();
        
        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            if (normalizedQuery.contains(entry.getKey()) || entry.getKey().contains(normalizedQuery)) {
                return "Search results for '" + query + "':\n\n" + entry.getValue();
            }
        }
        
        return "No specific information found for '" + query + "'. Please try a different search term.";
    }
}

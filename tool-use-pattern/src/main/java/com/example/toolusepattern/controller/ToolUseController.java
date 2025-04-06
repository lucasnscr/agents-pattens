package com.example.toolusepattern.controller;

import com.example.toolusepattern.service.ToolUseService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the Tool Use Pattern API
 */
@RestController
@RequestMapping("/api/tool-use")
public class ToolUseController {

    private final ToolUseService toolUseService;

    public ToolUseController(ToolUseService toolUseService) {
        this.toolUseService = toolUseService;
    }

    /**
     * Process a user query using the tool use pattern
     * 
     * @param query The user's query
     * @return The response from the LLM, potentially using tools
     */
    @GetMapping
    public String processQuery(@RequestParam String query) {
        return toolUseService.processQuery(query);
    }
}

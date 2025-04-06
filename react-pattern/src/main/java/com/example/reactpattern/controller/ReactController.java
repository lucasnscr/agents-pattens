package com.example.reactpattern.controller;

import com.example.reactpattern.service.ReactService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the ReAct Pattern API
 */
@RestController
@RequestMapping("/api/react")
public class ReactController {

    private final ReactService reactService;

    public ReactController(ReactService reactService) {
        this.reactService = reactService;
    }

    /**
     * Process a user query using the ReAct pattern
     * 
     * @param query The user's query
     * @return The response from the ReAct process
     */
    @GetMapping
    public String processQuery(@RequestParam String query) {
        return reactService.processQuery(query);
    }
}

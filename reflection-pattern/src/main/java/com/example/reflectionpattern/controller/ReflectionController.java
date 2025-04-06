package com.example.reflectionpattern.controller;

import com.example.reflectionpattern.model.ReflectionResponse;
import com.example.reflectionpattern.service.GeneratorService;
import com.example.reflectionpattern.service.ReflectorService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the Reflection Pattern API
 */
@RestController
@RequestMapping("/api/reflection")
public class ReflectionController {

    private final GeneratorService generatorService;
    private final ReflectorService reflectorService;

    public ReflectionController(GeneratorService generatorService, ReflectorService reflectorService) {
        this.generatorService = generatorService;
        this.reflectorService = reflectorService;
    }

    /**
     * Process a user query using the reflection pattern
     * 
     * @param query The user's query
     * @param iterations Optional number of reflection iterations (default: 1)
     * @return A response containing the initial output, reflected output, and final output
     */
    @GetMapping
    public ReflectionResponse processQuery(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int iterations) {
        
        // Generate initial output
        String initialOutput = generatorService.generateInitialOutput(query);
        
        // Reflect on and improve the output
        String reflectedOutput = reflectorService.iterativeReflection(query, initialOutput, iterations);
        
        return new ReflectionResponse(query, initialOutput, reflectedOutput);
    }
}

package com.example.reflectionpattern.model;

/**
 * Response model for the Reflection Pattern API
 */
public class ReflectionResponse {

    private final String query;
    private final String initialOutput;
    private final String reflectedOutput;

    public ReflectionResponse(String query, String initialOutput, String reflectedOutput) {
        this.query = query;
        this.initialOutput = initialOutput;
        this.reflectedOutput = reflectedOutput;
    }

    public String getQuery() {
        return query;
    }

    public String getInitialOutput() {
        return initialOutput;
    }

    public String getReflectedOutput() {
        return reflectedOutput;
    }
}

package com.example.toolusepattern.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Weather tool that provides current weather information for different locations
 */
@Component
public class WeatherTool {

    // Simulated weather data store
    private final Map<String, Map<String, Object>> weatherData;
    
    public WeatherTool() {
        // Initialize with some sample weather data
        weatherData = new HashMap<>();
        
        Map<String, Object> nycWeather = new HashMap<>();
        nycWeather.put("temperature", 72);
        nycWeather.put("condition", "Partly Cloudy");
        nycWeather.put("humidity", 65);
        nycWeather.put("windSpeed", 8);
        weatherData.put("new york", nycWeather);
        
        Map<String, Object> londonWeather = new HashMap<>();
        londonWeather.put("temperature", 58);
        londonWeather.put("condition", "Rainy");
        londonWeather.put("humidity", 80);
        londonWeather.put("windSpeed", 12);
        weatherData.put("london", londonWeather);
        
        Map<String, Object> tokyoWeather = new HashMap<>();
        tokyoWeather.put("temperature", 68);
        tokyoWeather.put("condition", "Sunny");
        tokyoWeather.put("humidity", 45);
        tokyoWeather.put("windSpeed", 5);
        weatherData.put("tokyo", tokyoWeather);
    }

    /**
     * Get current weather for a specified location
     * 
     * @param location The location to get weather for
     * @return Weather information as a formatted string
     */
    @Tool(description = "Get current weather information for a specified location")
    public String getCurrentWeather(String location) {
        String normalizedLocation = location.toLowerCase().trim();
        
        if (!weatherData.containsKey(normalizedLocation)) {
            return "Weather information for " + location + " is not available.";
        }
        
        Map<String, Object> weather = weatherData.get(normalizedLocation);
        
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        return String.format(
            "Weather for %s (as of %s):\n" +
            "Temperature: %d°F\n" +
            "Condition: %s\n" +
            "Humidity: %d%%\n" +
            "Wind Speed: %d mph",
            location, timestamp,
            weather.get("temperature"),
            weather.get("condition"),
            weather.get("humidity"),
            weather.get("windSpeed")
        );
    }
}

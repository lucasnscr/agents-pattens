package com.example.toolusepattern.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Calendar tool that provides date-related information and scheduling capabilities
 */
@Component
public class CalendarTool {

    // Simulated calendar data store
    private final Map<String, String> events;
    
    public CalendarTool() {
        // Initialize with some sample events
        events = new HashMap<>();
        
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        events.put(today.format(formatter), "Team meeting at 10:00 AM");
        events.put(today.plusDays(1).format(formatter), "Client presentation at 2:00 PM");
        events.put(today.plusDays(3).format(formatter), "Product launch planning at 11:00 AM");
    }

    /**
     * Get the current date
     * 
     * @return The current date in yyyy-MM-dd format
     */
    @Tool(description = "Get the current date")
    public String getCurrentDate() {
        LocalDate today = LocalDate.now();
        return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    /**
     * Check for events on a specific date
     * 
     * @param date The date to check in yyyy-MM-dd format
     * @return Event information or a message indicating no events
     */
    @Tool(description = "Check for events on a specific date (format: yyyy-MM-dd)")
    public String checkEvents(String date) {
        if (events.containsKey(date)) {
            return "Events for " + date + ": " + events.get(date);
        } else {
            return "No events scheduled for " + date;
        }
    }
    
    /**
     * Schedule a new event on a specific date
     * 
     * @param date The date for the event in yyyy-MM-dd format
     * @param eventDescription The description of the event
     * @return Confirmation message
     */
    @Tool(description = "Schedule a new event on a specific date (format: yyyy-MM-dd)")
    public String scheduleEvent(String date, String eventDescription) {
        events.put(date, eventDescription);
        return "Event scheduled for " + date + ": " + eventDescription;
    }
}

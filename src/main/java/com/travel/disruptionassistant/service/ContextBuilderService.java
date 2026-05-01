package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.DisruptionContext;
import com.travel.disruptionassistant.model.FlightEvent;
import org.springframework.stereotype.Service;

@Service
public class ContextBuilderService {

    private final MockDataService mockDataService;

    public ContextBuilderService(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    public DisruptionContext build(FlightEvent event) {
        DisruptionContext context = new DisruptionContext();
        context.setEvent(event);
        context.setItinerary(mockDataService.getMockItinerary("user123"));
        context.setPreferences(mockDataService.getMockPreferences());
        return context;
    }
}
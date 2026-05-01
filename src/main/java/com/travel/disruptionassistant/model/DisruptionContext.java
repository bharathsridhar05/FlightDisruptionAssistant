package com.travel.disruptionassistant.model;

import lombok.Data;

@Data
public class DisruptionContext {
    private FlightEvent event;
    private Itinerary itinerary;
    private UserPreferences preferences;
}
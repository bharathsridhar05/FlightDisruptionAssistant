package com.travel.disruptionassistant.model;

import lombok.Data;

@Data
public class FlightEvent {
    private String flightNumber;
    private String status; // DELAYED, CANCELLED
    private int delayMinutes;
}
package com.travel.disruptionassistant.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MockFlightService {

    public List<Map<String, Object>> getAlternateFlights(String flightNumber) {
        List<Map<String, Object>> flights = new ArrayList<>();

        Map<String, Object> f1 = new HashMap<>();
        f1.put("flightNumber", "AI205");
        f1.put("departure", "14:00");
        f1.put("arrival", "16:30");
        f1.put("cost", 3500);
        f1.put("isNight", false);
        flights.add(f1);

        Map<String, Object> f2 = new HashMap<>();
        f2.put("flightNumber", "AI208");
        f2.put("departure", "16:00");
        f2.put("arrival", "18:30");
        f2.put("cost", 2800);
        f2.put("isNight", false);
        flights.add(f2);

        Map<String, Object> f3 = new HashMap<>();
        f3.put("flightNumber", "AI210");
        f3.put("departure", "22:00");
        f3.put("arrival", "00:30");
        f3.put("cost", 1500);
        f3.put("isNight", true);
        flights.add(f3);

        return flights;
    }
}
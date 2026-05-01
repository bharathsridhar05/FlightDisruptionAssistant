package com.travel.disruptionassistant.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MockHotelService {

    public List<Map<String, Object>> getNearbyHotels() {
        List<Map<String, Object>> hotels = new ArrayList<>();

        Map<String, Object> h1 = new HashMap<>();
        h1.put("name", "Airport Comfort Inn");
        h1.put("costPerNight", 2000);
        h1.put("distanceKm", 1.2);
        hotels.add(h1);

        Map<String, Object> h2 = new HashMap<>();
        h2.put("name", "Transit Hotel Premium");
        h2.put("costPerNight", 3500);
        h2.put("distanceKm", 0.5);
        hotels.add(h2);

        return hotels;
    }
}
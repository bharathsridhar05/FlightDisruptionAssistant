package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.UserPreferences;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RankingService {

    public List<Map<String, Object>> rankFlights(
            List<Map<String, Object>> flights,
            UserPreferences preferences) {

        // Filter night flights if user prefers to avoid them
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> flight : flights) {
            boolean isNight = (boolean) flight.get("isNight");
            if (preferences.isAvoidNightFlights() && isNight) continue;
            filtered.add(flight);
        }

        // Sort by cost first, then earliest departure
        filtered.sort((a, b) -> {
            int costA = (int) a.get("cost");
            int costB = (int) b.get("cost");
            if (costA != costB) return costA - costB;
            String depA = (String) a.get("departure");
            String depB = (String) b.get("departure");
            return depA.compareTo(depB);
        });

        // Filter by budget
        List<Map<String, Object>> affordable = new ArrayList<>();
        for (Map<String, Object> flight : filtered) {
            if ((int) flight.get("cost") <= preferences.getBudget()) {
                affordable.add(flight);
            }
        }

        return affordable;
    }
}
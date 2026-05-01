package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.Itinerary;
import com.travel.disruptionassistant.model.UserPreferences;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MockDataService {

    public Itinerary getMockItinerary(String userId) {
        Itinerary itinerary = new Itinerary();
        itinerary.setUserId(userId);
        itinerary.setFlights(List.of("AI202", "AI303", "AI404"));
        return itinerary;
    }

    public UserPreferences getMockPreferences() {
        UserPreferences preferences = new UserPreferences();
        preferences.setBudget(5000);
        preferences.setAvoidNightFlights(true);
        return preferences;
    }
}
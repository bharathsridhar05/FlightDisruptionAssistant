package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecommendationService {

    private final MockFlightService mockFlightService;
    private final MockHotelService mockHotelService;
    private final RankingService rankingService;
    private final AIDecisionService aiDecisionService;

    public RecommendationService(MockFlightService mockFlightService,
                                 MockHotelService mockHotelService,
                                 RankingService rankingService,
                                 AIDecisionService aiDecisionService) {
        this.mockFlightService = mockFlightService;
        this.mockHotelService = mockHotelService;
        this.rankingService = rankingService;
        this.aiDecisionService = aiDecisionService;
    }

    public Map<String, Object> generateFullRecommendation(DisruptionContext context) {
        // Get AI recommendation
        Recommendation aiRec = aiDecisionService.generateRecommendation(context);

        // Get and rank alternate flights
        List<Map<String, Object>> alternateFlights = mockFlightService
                .getAlternateFlights(context.getEvent().getFlightNumber());
        List<Map<String, Object>> rankedFlights = rankingService
                .rankFlights(alternateFlights, context.getPreferences());

        // Get nearby hotels
        List<Map<String, Object>> hotels = mockHotelService.getNearbyHotels();

        // Combine everything
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("aiSummary", aiRec.getSummary());
        result.put("aiOptions", aiRec.getOptions());
        result.put("rankedAlternateFlights", rankedFlights);
        result.put("nearbyHotels", hotels);
        result.put("flightNumber", context.getEvent().getFlightNumber());
        result.put("delayMinutes", context.getEvent().getDelayMinutes());

        return result;
    }
}
package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.*;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class DisruptionService {

    private static final int DISRUPTION_THRESHOLD = 120;
    private final ContextBuilderService contextBuilderService;
    private final RecommendationService recommendationService;

    public DisruptionService(ContextBuilderService contextBuilderService,
                             RecommendationService recommendationService) {
        this.contextBuilderService = contextBuilderService;
        this.recommendationService = recommendationService;
    }

    public Map<String, Object> detect(FlightEvent event) {
        if (event.getDelayMinutes() > DISRUPTION_THRESHOLD) {
            System.out.println("⚠️ Disruption detected for flight: "
                    + event.getFlightNumber()
                    + " | Delay: " + event.getDelayMinutes() + " mins");
            DisruptionContext context = contextBuilderService.build(event);
            return recommendationService.generateFullRecommendation(context);
        }
        System.out.println("✅ No disruption for: " + event.getFlightNumber());
        return null;
    }
}
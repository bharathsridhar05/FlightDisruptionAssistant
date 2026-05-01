package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.*;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class DisruptionService {

    private static final int DISRUPTION_THRESHOLD = 120;
    private final ContextBuilderService contextBuilderService;
    private final RecommendationService recommendationService;
    private final NotificationService notificationService;

    public DisruptionService(ContextBuilderService contextBuilderService,
                             RecommendationService recommendationService,
                             NotificationService notificationService) {
        this.contextBuilderService = contextBuilderService;
        this.recommendationService = recommendationService;
        this.notificationService = notificationService;
    }

    public Map<String, Object> detect(FlightEvent event) {
        if (event.getDelayMinutes() > DISRUPTION_THRESHOLD) {
            System.out.println("⚠️ Disruption detected for flight: "
                    + event.getFlightNumber()
                    + " | Delay: " + event.getDelayMinutes() + " mins");
            DisruptionContext context = contextBuilderService.build(event);
            Map<String, Object> recommendation =
                    recommendationService.generateFullRecommendation(context);
            notificationService.sendSlackNotification(recommendation);
            return recommendation;
        }
        System.out.println("✅ No disruption for: " + event.getFlightNumber());
        return null;
    }
}
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
    private final RecommendationStore recommendationStore;
    private final LoggingService loggingService;

    public DisruptionService(ContextBuilderService contextBuilderService,
                             RecommendationService recommendationService,
                             NotificationService notificationService,
                             RecommendationStore recommendationStore,
                             LoggingService loggingService) {
        this.contextBuilderService = contextBuilderService;
        this.recommendationService = recommendationService;
        this.notificationService = notificationService;
        this.recommendationStore = recommendationStore;
        this.loggingService = loggingService;
    }

    public Map<String, Object> detect(FlightEvent event) {
        loggingService.logEvent(event.getFlightNumber(), event.getDelayMinutes());

        if (event.getDelayMinutes() > DISRUPTION_THRESHOLD) {
            loggingService.logDisruption(event.getFlightNumber());
            DisruptionContext context = contextBuilderService.build(event);
            Map<String, Object> recommendation =
                    recommendationService.generateFullRecommendation(context);
            loggingService.logAIGenerated();
            notificationService.sendSlackNotification(recommendation);
            recommendationStore.save(context.getItinerary().getUserId(), recommendation);
            loggingService.logNotificationSent(context.getItinerary().getUserId());
            return recommendation;
        }

        loggingService.logNoDisruption(event.getFlightNumber());
        return null;
    }
}
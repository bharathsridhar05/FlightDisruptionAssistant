package com.travel.disruptionassistant.controller;

import com.travel.disruptionassistant.service.RecommendationStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationStore recommendationStore;

    public RecommendationController(RecommendationStore recommendationStore) {
        this.recommendationStore = recommendationStore;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getRecommendation(@PathVariable String userId) {
        Map<String, Object> recommendation = recommendationStore.get(userId);
        if (recommendation.isEmpty()) {
            return ResponseEntity.ok("No recommendations found for user: " + userId);
        }
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping
    public ResponseEntity<?> getAllRecommendations() {
        return ResponseEntity.ok(recommendationStore.getAll());
    }
}
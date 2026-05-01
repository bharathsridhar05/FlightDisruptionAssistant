package com.travel.disruptionassistant.controller;

import com.travel.disruptionassistant.model.FlightEvent;
import com.travel.disruptionassistant.service.DisruptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class FlightEventController {

    private final DisruptionService disruptionService;

    public FlightEventController(DisruptionService disruptionService) {
        this.disruptionService = disruptionService;
    }

    @PostMapping("/flight")
    public ResponseEntity<?> receiveFlightEvent(@RequestBody FlightEvent event) {
        System.out.println("Received flight event: " + event);
        Map<String, Object> recommendation = disruptionService.detect(event);
        if (recommendation != null) {
            return ResponseEntity.ok(recommendation);
        }
        return ResponseEntity.ok("No disruption for: " + event.getFlightNumber());
    }
}
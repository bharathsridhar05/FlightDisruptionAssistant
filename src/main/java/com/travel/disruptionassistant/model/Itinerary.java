package com.travel.disruptionassistant.model;

import lombok.Data;
import java.util.List;

@Data
public class Itinerary {
    private String userId;
    private List<String> flights;
}
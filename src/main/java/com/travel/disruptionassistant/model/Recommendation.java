package com.travel.disruptionassistant.model;

import lombok.Data;
import java.util.List;

@Data
public class Recommendation {
    private String summary;
    private List<String> options;
}
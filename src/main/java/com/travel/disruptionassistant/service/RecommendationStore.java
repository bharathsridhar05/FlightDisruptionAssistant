package com.travel.disruptionassistant.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecommendationStore {

    private final Map<String, Map<String, Object>> store = new ConcurrentHashMap<>();

    public void save(String userId, Map<String, Object> recommendation) {
        store.put(userId, recommendation);
    }

    public Map<String, Object> get(String userId) {
        return store.getOrDefault(userId, Collections.emptyMap());
    }

    public Map<String, Map<String, Object>> getAll() {
        return Collections.unmodifiableMap(store);
    }
}
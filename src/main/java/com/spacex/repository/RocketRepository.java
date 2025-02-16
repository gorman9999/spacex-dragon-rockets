package com.spacex.repository;

import com.spacex.model.Rocket;
import java.util.HashMap;
import java.util.Map;

public class RocketRepository {
    private final Map<String, Rocket> rockets = new HashMap<>();

    public void addRocket(Rocket rocket) {
        rockets.put(rocket.getId(), rocket);
    }

    public Rocket getRocket(String id) {
        return rockets.get(id);
    }

    public Map<String, Rocket> getAllRockets() {
        return rockets;
    }
}
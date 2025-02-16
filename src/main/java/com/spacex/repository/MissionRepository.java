package com.spacex.repository;

import com.spacex.model.Mission;
import java.util.HashMap;
import java.util.Map;

public class MissionRepository {
    private final Map<String, Mission> missions = new HashMap<>();

    public void addMission(Mission mission) {
        missions.put(mission.getId(), mission);
    }

    public Mission getMission(String id) {
        return missions.get(id);
    }

    public Map<String, Mission> getAllMissions() {
        return missions;
    }
}
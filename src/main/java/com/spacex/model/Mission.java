package com.spacex.model;

import java.util.HashSet;
import java.util.Set;

public class Mission {
    private final String id;
    private final String name;
    private MissionStatus status;
    private final Set<String> rocketIds = new HashSet<>();

    public Mission(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = MissionStatus.SCHEDULED; // Initial status
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public MissionStatus getStatus() { return status; }
    public void setStatus(MissionStatus status) { this.status = status; }
    public Set<String> getRocketIds() { return rocketIds; }
    public void addRocket(String rocketId) { rocketIds.add(rocketId); }

    @Override
    public String toString() {
        return "Mission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", rocketIds=" + rocketIds +
                '}';
    }
}


package com.spacex.model;

public class Rocket {
    private final String id;
    private final String name;
    private RocketStatus status;

    public Rocket(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = RocketStatus.ON_GROUND; // Initial status
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public RocketStatus getStatus() { return status; }
    public void setStatus(RocketStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Rocket{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}


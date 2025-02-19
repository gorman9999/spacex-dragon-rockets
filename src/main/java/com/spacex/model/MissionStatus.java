package com.spacex.model;

public enum MissionStatus {
    SCHEDULED("Scheduled"), PENDING("Pending"), IN_PROGRESS("In progress"), ENDED("Ended");

    private final String text;

    MissionStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

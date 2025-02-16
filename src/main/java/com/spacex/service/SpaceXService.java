package com.spacex.service;

import com.spacex.model.Mission;
import com.spacex.model.MissionStatus;
import com.spacex.model.Rocket;
import com.spacex.model.RocketStatus;
import com.spacex.repository.MissionRepository;
import com.spacex.repository.RocketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SpaceXService {
    private final RocketRepository rocketRepository = new RocketRepository();
    private final MissionRepository missionRepository = new MissionRepository();

    public void addRocket(Rocket rocket) {
        rocketRepository.addRocket(rocket);
    }

    public void assignRocketToMission(String rocketId, String missionId) {
        Rocket rocket = rocketRepository.getRocket(rocketId);
        Mission mission = missionRepository.getMission(missionId);

        if (rocket == null || mission == null) {
            throw new IllegalArgumentException("Rocket or Mission not found.");
        }

        Mission currentMission = findMissionByRocketId(rocketId);
        if (currentMission != null && !currentMission.getId().equals(missionId)) {
            throw new IllegalStateException("Rocket is already assigned to mission: " + currentMission.getName());
        }

        mission.addRocket(rocketId);
        rocket.setStatus(RocketStatus.IN_SPACE);
        updateMissionStatus(mission);
    }

    public void changeRocketStatus(String rocketId, RocketStatus status) {
        Rocket rocket = rocketRepository.getRocket(rocketId);
        if (rocket == null) {
            throw new IllegalArgumentException("Rocket  not found.");
        }

        rocket.setStatus(status);
        updateMissionStatusForRocket(rocket);
    }

    public void addMission(Mission mission) {
        missionRepository.addMission(mission);
    }

    public void assignRocketsToMission(Set<String> rocketIds, String missionId) {
        Mission mission = missionRepository.getMission(missionId);
        if (mission == null) {
            throw new IllegalArgumentException("Mission  not found.");
        }

        for (String rocketId : rocketIds) {
            assignRocketToMission(rocketId, missionId);
        }
    }

    public void changeMissionStatus(String missionId, MissionStatus status) {
        Mission mission = missionRepository.getMission(missionId);
        if (mission == null) {
            throw new IllegalArgumentException("Mission  not found.");
        }

        mission.setStatus(status);
    }

    public List<Mission> getMissionSummary() {
        List<Mission> missions = new ArrayList<>(missionRepository.getAllMissions().values());
        missions.sort((m1, m2) -> {
            int rocketCountComparison = Integer.compare(m2.getRocketIds().size(), m1.getRocketIds().size());
            if (rocketCountComparison == 0) {
                return m2.getName().compareTo(m1.getName()); // Descending alphabetical order
            }
            return rocketCountComparison;
        });
        return missions;
    }

    private Mission findMissionByRocketId(String rocketId) {
        for (Mission mission : missionRepository.getAllMissions().values()) {
            if (mission.getRocketIds().contains(rocketId)) {
                return mission;
            }
        }
        return null;
    }

    private void updateMissionStatus(Mission mission) {
        boolean hasRocketsInRepair = mission.getRocketIds().stream()
                .anyMatch(rocketId -> rocketRepository.getRocket(rocketId).getStatus() == RocketStatus.IN_REPAIR);

        if (mission.getRocketIds().isEmpty()) {
            mission.setStatus(MissionStatus.SCHEDULED);
        } else if (hasRocketsInRepair) {
            mission.setStatus(MissionStatus.PENDING);
        } else {
            mission.setStatus(MissionStatus.IN_PROGRESS);
        }
    }

    private void updateMissionStatusForRocket(Rocket rocket) {
        missionRepository.getAllMissions().values().forEach(mission -> {
            if (mission.getRocketIds().contains(rocket.getId())) {
                updateMissionStatus(mission);
            }
        });
    }
}
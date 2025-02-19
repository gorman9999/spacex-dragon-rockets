package com.spacex.service;

import com.spacex.model.Mission;
import com.spacex.model.MissionStatus;
import com.spacex.model.Rocket;
import com.spacex.model.RocketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceXServiceTest {
    private SpaceXService spaceXService;

    @BeforeEach
    public void setUp() {
        spaceXService = new SpaceXService();
    }

    @Test
    public void testAddRocket() {
        Rocket rocket = new Rocket("1", "Dragon 1");
        spaceXService.addRocket(rocket);
        assertEquals(RocketStatus.ON_GROUND, rocket.getStatus());
    }

    @Test
    public void testAssignRocketToMission() {
        Rocket rocket = new Rocket("1", "Dragon 1");
        Mission mission = new Mission("1", "Mars");
        spaceXService.addRocket(rocket);
        spaceXService.addMission(mission);
        spaceXService.assignRocketToMission("1", "1");
        assertEquals(RocketStatus.IN_SPACE, rocket.getStatus());
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());
    }

    @Test
    public void testChangeRocketStatus() {
        Rocket rocket = new Rocket("1", "Dragon 1");
        spaceXService.addRocket(rocket);
        spaceXService.changeRocketStatus("1", RocketStatus.IN_REPAIR);
        assertEquals(RocketStatus.IN_REPAIR, rocket.getStatus());
    }

    @Test
    public void testChangeMissionStatus() {
        Mission mission = new Mission("1", "Mars");
        spaceXService.addMission(mission);
        assertEquals(MissionStatus.SCHEDULED, mission.getStatus());

        spaceXService.changeMissionStatus("1", MissionStatus.IN_PROGRESS);
        assertEquals(MissionStatus.IN_PROGRESS, mission.getStatus());

        spaceXService.changeMissionStatus("1", MissionStatus.ENDED);
        assertEquals(MissionStatus.ENDED, mission.getStatus());
    }

    @Test
    public void testRocketCanOnlyBeAssignedToOneMission() {
        Rocket rocket = new Rocket("1", "Dragon 1");
        Mission mission1 = new Mission("1", "Mars");
        Mission mission2 = new Mission("2", "Luna");

        spaceXService.addRocket(rocket);
        spaceXService.addMission(mission1);
        spaceXService.addMission(mission2);

        spaceXService.assignRocketToMission("1", "1");
        assertEquals(MissionStatus.IN_PROGRESS, mission1.getStatus());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            spaceXService.assignRocketToMission("1", "2");
        });

        String expectedMessage = "Rocket is already assigned to mission: Mars";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetMissionSummary() {
        Rocket rocket1 = new Rocket("1", "Dragon 1");
        Rocket rocket2 = new Rocket("2", "Dragon 2");

        Mission mission1 = new Mission("1", "Mars");
        Mission mission2 = new Mission("2", "Earth");
        Mission mission3 = new Mission("3", "Jupiter");

        spaceXService.addRocket(rocket1);
        spaceXService.addRocket(rocket2);
        spaceXService.addMission(mission1);
        spaceXService.addMission(mission2);
        spaceXService.addMission(mission3);

        spaceXService.assignRocketsToMission(Set.of("1"), "1");
        spaceXService.assignRocketsToMission(Set.of("2"), "2");

        String summary = spaceXService.getMissionSummary();

        assertEquals("● Mars – In progress – Dragons: 1\n" +
                "   ● Dragon 1 – In space\n" +
                "● Earth – In progress – Dragons: 1\n" +
                "   ● Dragon 2 – In space\n" +
                "● Jupiter – Scheduled – Dragons: 0", summary);
    }
}
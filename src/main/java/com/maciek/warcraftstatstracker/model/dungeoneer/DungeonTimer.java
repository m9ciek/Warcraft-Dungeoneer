package com.maciek.warcraftstatstracker.model.dungeoneer;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class DungeonTimer {

    private static final LocalTime SIEGE_OF_BORALUS = LocalTime.of(0, 36, 0);
    private static final LocalTime ATAL_DAZAR = LocalTime.of(0, 30, 0);
    private static final LocalTime KINGS_REST = LocalTime.of(0, 42, 0);
    private static final LocalTime SHRINE_OF_THE_STORM = LocalTime.of(0, 42, 0);
    private static final LocalTime TEMPLE_OF_SETHRALISS = LocalTime.of(0, 36, 0);
    private static final LocalTime WAYCREST_MANOR = LocalTime.of(0, 39, 0);
    private static final LocalTime TOL_DAGOR = LocalTime.of(0, 36, 0);
    private static final LocalTime MECHAGON_WORKSHOP = LocalTime.of(0, 32, 0);
    private static final LocalTime FREEHOLD = LocalTime.of(0, 33, 0);
    private static final LocalTime THE_MOTHERLODE = LocalTime.of(0, 39, 0);
    private static final LocalTime MECHAGON_JUNKYARD = LocalTime.of(0, 38, 0);
    private static final LocalTime THE_UNDERROT = LocalTime.of(0, 33, 0);

    private static Map<String, LocalTime> dungeonTimerMap = new HashMap<>();

    private DungeonTimer() {
    }

    @PostConstruct
    private static void populateDungeonTimerMap() {
        dungeonTimerMap.put("Atal'Dazar", ATAL_DAZAR);
        dungeonTimerMap.put("Freehold", FREEHOLD);
        dungeonTimerMap.put("Shrine of the Storm", SHRINE_OF_THE_STORM);
        dungeonTimerMap.put("Operation: Mechagon - Workshop", MECHAGON_WORKSHOP);
        dungeonTimerMap.put("The MOTHERLODE!!", THE_MOTHERLODE);
        dungeonTimerMap.put("Operation: Mechagon - Junkyard", MECHAGON_JUNKYARD);
        dungeonTimerMap.put("Temple of Sethraliss", TEMPLE_OF_SETHRALISS);
        dungeonTimerMap.put("Kings' Rest", KINGS_REST);
        dungeonTimerMap.put("Tol Dagor", TOL_DAGOR);
        dungeonTimerMap.put("Waycrest Manor", WAYCREST_MANOR);
        dungeonTimerMap.put("The Underrot", THE_UNDERROT);
        dungeonTimerMap.put("Siege of Boralus", SIEGE_OF_BORALUS);
    }

    public static LocalTime getDungeonTimer(String dungeonName) {
        return dungeonTimerMap.get(dungeonName);
    }
}

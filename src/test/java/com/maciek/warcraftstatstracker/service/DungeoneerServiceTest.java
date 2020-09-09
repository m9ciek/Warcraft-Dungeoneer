package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.model.dungeoneer.MythicPlusDungeon;
import com.maciek.warcraftstatstracker.service.dungeoneer.DungeoneerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DungeoneerServiceTest {

    @InjectMocks
    private DungeoneerService dungeoneerService;

    private List<MythicPlusDungeon> mythicPlusDungeonList;
    private DungeonData dungeonData;

    @BeforeEach
    void setUp() {
        MythicPlusDungeon freeholdDungeon = MythicPlusDungeon.builder()
                .id(1)
                .timer(LocalTime.of(0, 33, 0))
                .duration(LocalTime.of(0, 24, 25))
                .completedDate(LocalDateTime.now())
                .keystoneLevel(15)
                .name("Freehold")
                .isCompletedWithinTime(true)
                .build();

        MythicPlusDungeon siegeOfBoralusDungeon = MythicPlusDungeon.builder()
                .id(2)
                .timer(LocalTime.of(0, 36, 0))
                .duration(LocalTime.of(0, 38, 18))
                .completedDate(LocalDateTime.now())
                .keystoneLevel(11)
                .name("Siege of Boralus")
                .isCompletedWithinTime(false)
                .build();

        mythicPlusDungeonList = new ArrayList<>();
        mythicPlusDungeonList.add(freeholdDungeon);
        mythicPlusDungeonList.add(siegeOfBoralusDungeon);


        dungeonData = DungeonData.builder()
                .season(4)
                .mythicPlusDungeons(mythicPlusDungeonList)
                .build();
    }

    @Test
    void should_calculate_correct_DungeonScore() {
        double completedDungeonScore = dungeoneerService.calculateDungeonScore(mythicPlusDungeonList.get(0));
        double failedDungeonScore = dungeoneerService.calculateDungeonScore(mythicPlusDungeonList.get(1));

        assertAll(
                () -> assertEquals(201.5, completedDungeonScore),
                () -> assertEquals(96.2, failedDungeonScore)
        );
    }

    @Test
    void should_calculate_correct_total_score() {
        mythicPlusDungeonList.get(0).setScore(201.5);
        mythicPlusDungeonList.get(1).setScore(96.2);

        dungeonData.setMythicPlusDungeons(mythicPlusDungeonList);

        assertEquals(297.7, dungeoneerService.calculateTotalScore(dungeonData));
    }

    @Test
    void should_sort_dungeonData_descending_by_score() {
        mythicPlusDungeonList.get(0).setScore(25.5);
        mythicPlusDungeonList.get(1).setScore(128.2);
        mythicPlusDungeonList.add(MythicPlusDungeon.builder()
                .id(1)
                .score(87.2)
                .build());

        List<MythicPlusDungeon> descSortedDungeonList = mythicPlusDungeonList;
        descSortedDungeonList.sort(Comparator.comparing(MythicPlusDungeon::getScore).reversed());

        assertEquals(descSortedDungeonList, dungeoneerService.sortDungeonDataDsc(mythicPlusDungeonList));

    }
}
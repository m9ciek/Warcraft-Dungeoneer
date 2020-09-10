package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.model.dungeoneer.MythicPlusDungeon;
import com.maciek.warcraftstatstracker.service.dungeoneer.DungeoneerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DungeoneerControllerTest {

    @Mock
    private DungeoneerService dungeoneerServiceMock;

    @InjectMocks
    private DungeoneerController dungeoneerController;

    private DungeonData dungeonData;

    @BeforeEach
    void setUp() {
        MythicPlusDungeon freeholdDungeon = MythicPlusDungeon.builder()
                .id(1)
                .build();

        List<MythicPlusDungeon> mythicPlusDungeonList = new ArrayList<>();
        mythicPlusDungeonList.add(freeholdDungeon);
        dungeonData = DungeonData.builder()
                .season(4)
                .mythicPlusDungeons(mythicPlusDungeonList)
                .build();
    }

    @Test
    void should_return_ok_with_entity() {
        when(dungeoneerServiceMock.getDungeonData("Faravir", "Tarren Mill", 4, null)).thenReturn(this.dungeonData);
        ResponseEntity entity = dungeoneerController.getDungeonData("Faravir", "Tarren Mill", 4, null);
        assertEquals(ResponseEntity.ok(dungeonData), entity);
    }

    @Test
    void should_return_empty_entity() {
        when(dungeoneerServiceMock.getDungeonData(anyString(), anyString(), anyInt(), any())).thenReturn(null);
        ResponseEntity entity = dungeoneerController.getDungeonData("Invalid", "Invalid", 0, null);
        assertEquals(ResponseEntity.ok(null), entity);
    }
}
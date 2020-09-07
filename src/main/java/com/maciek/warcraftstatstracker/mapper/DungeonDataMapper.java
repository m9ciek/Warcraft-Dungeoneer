package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonTimer;
import com.maciek.warcraftstatstracker.model.dungeoneer.KeystoneAffix;
import com.maciek.warcraftstatstracker.model.dungeoneer.MythicPlusDungeon;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class DungeonDataMapper {

    public static DungeonData mapJSONToDungeonData(String jsonData) throws JsonProcessingException {
        DungeonData dungeonData = new DungeonData();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readValue(jsonData, JsonNode.class);

        dungeonData.setSeason(node.get("season").get("id").asInt());
        List<MythicPlusDungeon> mythicPlusDungeonList = mapToMythicPlusDungeonList(node);

        dungeonData.setMythicPlusDungeons(mythicPlusDungeonList);
        return dungeonData;
    }

    private static List<MythicPlusDungeon> mapToMythicPlusDungeonList(JsonNode jsonResponseNode) {
        List<MythicPlusDungeon> mythicPlusDungeonList = new ArrayList<>();

        JsonNode parentNode = jsonResponseNode.get("best_runs");
        for (int i = 0; i < parentNode.size(); i++) {
            JsonNode childNode = parentNode.get(i);

            LocalDateTime completedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(childNode.get("completed_timestamp").asLong()), ZoneId.systemDefault());
            LocalTime duration = LocalTime.ofSecondOfDay(childNode.get("duration").asLong() / 1000);

            Set<KeystoneAffix> keystoneAffixes = mapToKeystoneAffixSet(childNode);

            MythicPlusDungeon dungeon = MythicPlusDungeon.builder()
                    .id(childNode.get("dungeon").get("id").asInt())
                    .name(childNode.get("dungeon").get("name").asText())
                    .isCompletedWithinTime(childNode.get("is_completed_within_time").asBoolean())
                    .keystoneLevel(childNode.get("keystone_level").asInt())
                    .completedDate(completedDate)
                    .duration(duration)
                    .affixes(keystoneAffixes)
                    .build();
            dungeon.setTimer(DungeonTimer.getDungeonTimer(dungeon.getName()));
            mythicPlusDungeonList.add(dungeon);
        }
        return mythicPlusDungeonList;
    }

    private static Set<KeystoneAffix> mapToKeystoneAffixSet(JsonNode bestRunElement) {
        Set<KeystoneAffix> keystoneAffixes = new HashSet<>();

        for (int i = 0; i < bestRunElement.get("keystone_affixes").size(); i++) {
            JsonNode affixNode = bestRunElement.get("keystone_affixes").get(i);
            KeystoneAffix affix = KeystoneAffix.builder()
                    .id(affixNode.get("id").asInt())
                    .href(affixNode.get("key").get("href").asText())
                    .name(affixNode.get("name").asText())
                    .build();

            keystoneAffixes.add(affix);
        }
        return keystoneAffixes;
    }

}

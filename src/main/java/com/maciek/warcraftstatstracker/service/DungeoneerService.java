package com.maciek.warcraftstatstracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.model.dungeoneer.KeystoneAffix;
import com.maciek.warcraftstatstracker.model.dungeoneer.MythicPlusDungeon;
import com.maciek.warcraftstatstracker.service.api.BlizzardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DungeoneerService {

    private final BlizzardApiService blizzardApiService;

    @Autowired
    public DungeoneerService(BlizzardApiService blizzardApiService) {
        this.blizzardApiService = blizzardApiService;
    }

    public DungeonData getDungeonDataFromApi(String characterName, String realm, int season, OAuth2Authentication oAuth2Authentication) throws JsonProcessingException {
        ResponseEntity<String> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" + realm + "/" + characterName + "/mythic-keystone-profile/season/" + season + "?namespace=profile-eu&locale=en_US",
                String.class, oAuth2Authentication);

        DungeonData dungeonData = new DungeonData();
        List<MythicPlusDungeon> mythicPlusDungeonList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readValue(response.getBody(), JsonNode.class);

        dungeonData.setSeason(node.get("season").get("id").asInt());

        JsonNode parentNode = node.get("best_runs");

        for (int i = 0; i < parentNode.size(); i++) {
            JsonNode childNode = parentNode.get(i);

            LocalDateTime completedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(childNode.get("completed_timestamp").asLong()), ZoneId.systemDefault());
            LocalTime duration = LocalTime.ofSecondOfDay(childNode.get("duration").asLong() / 1000);
//            LocalTime timer;

            Set<KeystoneAffix> keystoneAffixes = new HashSet<>();

            for (int j = 0; j < childNode.get("keystone_affixes").size(); j++) {
                JsonNode affixNode = childNode.get("keystone_affixes").get(j);
                KeystoneAffix affix = KeystoneAffix.builder()
                        .id(affixNode.get("id").asInt())
                        .href(affixNode.get("key").get("href").asText())
                        .name(affixNode.get("name").asText())
                        .build();

                keystoneAffixes.add(affix);
            }

            MythicPlusDungeon dungeon = MythicPlusDungeon.builder()
                    .id(childNode.get("dungeon").get("id").asInt())
                    .name(childNode.get("dungeon").get("name").asText())
                    .isCompletedWithinTime(childNode.get("is_completed_within_time").asBoolean())
                    .keystoneLevel(childNode.get("keystone_level").asInt())
                    .completedDate(completedDate)
                    .duration(duration)
                    .timer(duration) //subject to change
                    .affixes(keystoneAffixes)
                    .build();
            mythicPlusDungeonList.add(dungeon);
        }
        dungeonData.setMythicPlusDungeons(mythicPlusDungeonList);
        return dungeonData;
    }
}

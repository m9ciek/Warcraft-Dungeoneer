package com.maciek.warcraftstatstracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maciek.warcraftstatstracker.mapper.DungeonDataMapper;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.model.dungeoneer.MythicPlusDungeon;
import com.maciek.warcraftstatstracker.service.api.BlizzardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DungeoneerService {

    private final BlizzardApiService blizzardApiService;

    @Autowired
    public DungeoneerService(BlizzardApiService blizzardApiService) {
        this.blizzardApiService = blizzardApiService;
    }

    public DungeonData getDungeonData(String characterName, String realm, int season, OAuth2Authentication oAuth2Authentication) throws JsonProcessingException {
        ResponseEntity<String> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" + realm + "/" + characterName + "/mythic-keystone-profile/season/" + season + "?namespace=profile-eu&locale=en_US",
                String.class, oAuth2Authentication);

        DungeonData playerDungeonData = DungeonDataMapper.mapJSONToDungeonData(response.getBody());
        List<MythicPlusDungeon> mythicPlusDungeons = playerDungeonData.getMythicPlusDungeons();
        mythicPlusDungeons
                .forEach(e -> e.setScore(calculateDungeonScore(e)));
        playerDungeonData.setMythicPlusDungeons(sortDungeonDataAsc(mythicPlusDungeons));

        double playerTotalScore = calculateTotalScore(playerDungeonData);
        playerDungeonData.setTotalScore(playerTotalScore);

        return playerDungeonData;
    }

    public double calculateDungeonScore(MythicPlusDungeon mythicPlusDungeon) {
        LocalTime timer = mythicPlusDungeon.getTimer();
        LocalTime completedDuration = mythicPlusDungeon.getDuration();
        int keystoneLevel = mythicPlusDungeon.getKeystoneLevel();
        double dungeonScore = keystoneLevel * 10.0;

        if (mythicPlusDungeon.isCompletedWithinTime()) {
            int secondsDiff = timer.toSecondOfDay() - completedDuration.toSecondOfDay();
            dungeonScore += (secondsDiff / 1.0) * 0.1; //each 0.1 point == 1.0 sec.
        } else {
            int secondsDiff = completedDuration.toSecondOfDay() - timer.toSecondOfDay();
            dungeonScore -= (secondsDiff / 1.0) * 0.1;
        }

        if (dungeonScore < 0.0) {
            return 0;
        }
        return Math.round(dungeonScore * 10.0) / 10.0; //round up to 1 decimal place
    }

    /*
    Sum of best scores for each dungeon
     */
    public double calculateTotalScore(DungeonData dungeonData) {
        List<MythicPlusDungeon> mythicPlusDungeons = dungeonData.getMythicPlusDungeons();
        return mythicPlusDungeons.stream().distinct().mapToDouble(MythicPlusDungeon::getScore).sum();
    }

    public List<MythicPlusDungeon> sortDungeonDataAsc(List<MythicPlusDungeon> mythicPlusDungeons) {
        return mythicPlusDungeons.stream()
                .sorted(Comparator.comparing(MythicPlusDungeon::getScore).reversed())
                .collect(Collectors.toList());
    }
}

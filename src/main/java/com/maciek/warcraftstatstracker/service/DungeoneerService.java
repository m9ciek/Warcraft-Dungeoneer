package com.maciek.warcraftstatstracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maciek.warcraftstatstracker.mapper.DungeonDataMapper;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.service.api.BlizzardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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

        return DungeonDataMapper.mapJSONToDungeonData(response.getBody());
    }
}

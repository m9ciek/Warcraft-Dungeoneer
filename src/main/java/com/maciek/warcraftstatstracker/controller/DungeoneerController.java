package com.maciek.warcraftstatstracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.service.DungeoneerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dungeoneer")
public class DungeoneerController {

    private final DungeoneerService dungeoneerService;

    @Autowired
    public DungeoneerController(DungeoneerService dungeoneerService) {
        this.dungeoneerService = dungeoneerService;
    }

    @GetMapping("/data/{characterName}")
    public DungeonData getDungeonData(@PathVariable String characterName, @RequestParam String realm, @RequestParam int season, OAuth2Authentication oAuth2Authentication) throws JsonProcessingException {
        return dungeoneerService.getDungeonDataFromApi(characterName, realm, season, oAuth2Authentication);
    }
}

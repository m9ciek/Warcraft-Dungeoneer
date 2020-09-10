package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.model.dungeoneer.DungeonData;
import com.maciek.warcraftstatstracker.service.dungeoneer.DungeoneerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DungeonData> getDungeonData(@PathVariable String characterName, @RequestParam String realm, @RequestParam int season, OAuth2Authentication oAuth2Authentication) {
        return ResponseEntity.ok(dungeoneerService.getDungeonData(characterName, realm, season, oAuth2Authentication));
    }
}

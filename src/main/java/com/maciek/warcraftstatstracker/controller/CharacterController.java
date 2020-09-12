package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/character/{name}")
    public ResponseEntity<Character> getCharacterData(@PathVariable String name, @RequestParam String realm, OAuth2Authentication oAuth2Authentication) {
        Character character = characterService.getCharacterFromApi(name, realm, oAuth2Authentication);
        return ResponseEntity.ok(character);
    }
}

package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.model.WowProfile;
import com.maciek.warcraftstatstracker.service.BlizzardApiService;
import com.maciek.warcraftstatstracker.service.UserTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user-data")
public class UserTrackerController {

    private UserTrackerService userTrackerService;
    private BlizzardApiService blizzardApiService;

    @Autowired
    public UserTrackerController(UserTrackerService userTrackerService, BlizzardApiService blizzardApiService) {
        this.userTrackerService = userTrackerService;
        this.blizzardApiService = blizzardApiService;
    }

    @GetMapping
    public ResponseEntity<User> getUserData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<User> response = blizzardApiService.getRequestBlizzardApi("https://eu.battle.net/oauth/userinfo", User.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/wow-profile")
    public ResponseEntity<WowProfile> getWowData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<WowProfile> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
                WowProfile.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

//    @GetMapping("/character/{name}")
//    public ResponseEntity<Character> getLoggedUserCharacterData(@PathVariable String name, OAuth2Authentication oAuth2Authentication) {
//        ResponseEntity<WowProfile> response = blizzardApiService.requestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
//                HttpMethod.GET, WowProfile.class, oAuth2Authentication);
//        Character character;
//        try {
//            character = userTrackerService.getCharactersForWowProfile(response.getBody(), name).get(0);
//        } catch (IndexOutOfBoundsException e) {
//            return ResponseEntity.notFound().build();
//        }
//        CharacterDetails characterDetails = character.getCharacterDetails();
//        ResponseEntity<String> request = blizzardApiService.requestBlizzardApi(characterDetails.getUrl() + "&locale=en_us",
//                HttpMethod.GET, String.class, oAuth2Authentication);
//
//        character.setCharacterDetails(CharacterDetailsMapper.mapJSONToCharacterDetails(request.getBody(), characterDetails));
//
//        return ResponseEntity.ok(character);
//    }

}

package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.model.UserProfile;
import com.maciek.warcraftstatstracker.service.BlizzardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user-data")
public class UserDataController {

    private BlizzardApiService blizzardApiService;

    @Autowired
    public UserDataController(BlizzardApiService blizzardApiService) {
        this.blizzardApiService = blizzardApiService;
    }

    @GetMapping
    public ResponseEntity<User> getUserData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<User> response = blizzardApiService.getRequestBlizzardApi("https://eu.battle.net/oauth/userinfo", User.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/wow-profile")
    public ResponseEntity<UserProfile> getWowData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<UserProfile> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
                UserProfile.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

}

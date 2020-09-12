package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.dto.UserCharacterDTO;
import com.maciek.warcraftstatstracker.external.api.BlizzardApiService;
import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.service.user.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/user-data")
public class UserDataController {

    private final BlizzardApiService blizzardApiService;
    private final UserDataService userDataService;

    @Autowired
    public UserDataController(BlizzardApiService blizzardApiService, UserDataService userDataService) {
        this.blizzardApiService = blizzardApiService;
        this.userDataService = userDataService;
    }

    @GetMapping
    public ResponseEntity<User> getUserData(OAuth2Authentication oAuth2Authentication) {
        User user = userDataService.getUserData(oAuth2Authentication);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/wow-profile")
    public ResponseEntity<?> getWowData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<String> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_GB",
                String.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/user-characters")
    public ResponseEntity<List<UserCharacterDTO>> getUserActiveCharacters(OAuth2Authentication oAuth2Authentication) {
        return ResponseEntity.ok(userDataService.getCharactersForActiveUser(oAuth2Authentication));
    }

}

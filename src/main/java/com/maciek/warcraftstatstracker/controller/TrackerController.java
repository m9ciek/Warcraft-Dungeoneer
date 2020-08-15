package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.mapper.CharacterDetailsMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.CharacterDetails;
import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.model.WowProfile;
import com.maciek.warcraftstatstracker.service.TrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
public class TrackerController {

    private final Logger logger = LoggerFactory.getLogger(TrackerController.class);

    private TrackerService trackerService;

    @Autowired
    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @GetMapping("/user-data")
    public ResponseEntity<User> getUserData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<User> response = requestBlizzardApi("https://eu.battle.net/oauth/userinfo", HttpMethod.GET, User.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/wow-profile")
    public ResponseEntity<WowProfile> getWowData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<WowProfile> response = requestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
                HttpMethod.GET, WowProfile.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/character/{name}")
    public Character getLoggedUserCharacterData(@PathVariable String name, OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<WowProfile> response = requestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
                HttpMethod.GET, WowProfile.class, oAuth2Authentication);


        Character character = trackerService.getCharactersForWowProfile(response.getBody(), name).get(0);
        CharacterDetails characterDetails = character.getCharacterDetails();

        ResponseEntity<String> request = requestBlizzardApi(characterDetails.getUrl() + "&locale=en_us",
                HttpMethod.GET, String.class, oAuth2Authentication);

        character.setCharacterDetails(CharacterDetailsMapper.mapJSONToCharacterDetails(request.getBody(), characterDetails));

        return character;
    }

    //returns multiple characters in case of same name on different realm
    @GetMapping("/a/{name}")
    public Character getNode(@PathVariable String name, OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    private <T> ResponseEntity<T> requestBlizzardApi(String url, HttpMethod httpMethod, Class<T> responseType, OAuth2Authentication oAuth2Authentication) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = addAuthorizationHeader(oAuth2Authentication);
        return restTemplate.exchange(url, httpMethod, httpEntity, responseType);
    }

    private HttpEntity addAuthorizationHeader(OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }

}

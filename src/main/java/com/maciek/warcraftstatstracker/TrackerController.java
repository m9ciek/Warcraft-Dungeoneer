package com.maciek.warcraftstatstracker;

import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.model.WowAccount;
import com.maciek.warcraftstatstracker.model.WowProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TrackerController {

    private final Logger logger = LoggerFactory.getLogger(TrackerController.class);

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

    //returns multiple characters in case of same name on different realm
    @GetMapping("/character/{name}")
    public List<Character> getCharacterData(@PathVariable String name, OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<WowProfile> response = requestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_EU",
                HttpMethod.GET, WowProfile.class, oAuth2Authentication);
        WowAccount wowAccount = response.getBody().getWowAccounts().stream().findFirst().get();
        List<Character> foundCharacters = wowAccount.getCharacters().stream()
                .filter(e -> e.getName().equals(capitalize(name)))
                .collect(Collectors.toList());

        return foundCharacters;
    }

    private <T> ResponseEntity<T> requestBlizzardApi(String url, HttpMethod httpMethod, Class<T> responseType, OAuth2Authentication oAuth2Authentication) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = addAuthorizationToHeader(oAuth2Authentication);
        return restTemplate.exchange(url, httpMethod, httpEntity, responseType);
    }

    private HttpEntity addAuthorizationToHeader(OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        logger.info("User token: " + token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }

    private static String capitalize(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}

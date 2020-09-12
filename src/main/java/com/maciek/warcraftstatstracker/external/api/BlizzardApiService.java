package com.maciek.warcraftstatstracker.external.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class BlizzardApiService {

    private final Logger logger = LoggerFactory.getLogger(BlizzardApiService.class);

    @Async
    public CompletableFuture<String> getCharacterData(String characterName, String realm, OAuth2Authentication oAuth2Authentication) {
        System.out.println("Blizz APi");
        ResponseEntity<String> blizzardApiResponse =
                getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" + correctRealmName(realm) + "/" + characterName.toLowerCase() + "?namespace=profile-eu&locale=en_US",
                        String.class, oAuth2Authentication);
        return CompletableFuture.completedFuture(blizzardApiResponse.getBody());
    }

    public String getDungeonData(String characterName, String realm, int season, OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<String> response = getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" + realm + "/" + characterName + "/mythic-keystone-profile/season/" + season + "?namespace=profile-eu&locale=en_US",
                String.class, oAuth2Authentication);

        return response.getBody();
    }

    public <T> ResponseEntity<T> getRequestBlizzardApi(String url, Class<T> responseType, OAuth2Authentication oAuth2Authentication) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = addAuthorizationHeader(oAuth2Authentication);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    private HttpEntity addAuthorizationHeader(OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        logger.info("User token: " + token); //for testing purposes
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }

    private String correctRealmName(String realmName) {
        return realmName.toLowerCase().trim().replace(" ", "-");
    }
}

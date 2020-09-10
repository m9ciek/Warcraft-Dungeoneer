package com.maciek.warcraftstatstracker.external.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlizzardApiService {

    private final Logger logger = LoggerFactory.getLogger(BlizzardApiService.class);
    private OAuth2Authentication authentication;

    public String getCharacterData(String characterName, String realm) {
        ResponseEntity<String> blizzardApiResponse =
                getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" + correctRealmName(realm) + "/" + characterName.toLowerCase() + "?namespace=profile-eu&locale=en_US",
                        String.class, authentication);
        return blizzardApiResponse.getBody();
    }

    public <T> ResponseEntity<T> getRequestBlizzardApi(String url, Class<T> responseType, OAuth2Authentication oAuth2Authentication) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = addAuthorizationHeader(oAuth2Authentication);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    public void authenticateOAuth2(OAuth2Authentication oAuth2Authentication) {
        this.authentication = oAuth2Authentication;
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

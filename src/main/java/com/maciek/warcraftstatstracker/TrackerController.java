package com.maciek.warcraftstatstracker;

import com.maciek.warcraftstatstracker.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
public class TrackerController {

    private final Logger logger = LoggerFactory.getLogger(TrackerController.class);

    @GetMapping("/user-data")
    public ResponseEntity<User> getUserData(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<User> response = requestBlizzardApi("https://eu.battle.net/oauth/userinfo", HttpMethod.GET, User.class, oAuth2Authentication);
        return ResponseEntity.ok(response.getBody());
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

}

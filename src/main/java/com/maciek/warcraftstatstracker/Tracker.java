package com.maciek.warcraftstatstracker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class Tracker {

    @GetMapping("/hello")
    public String hello() {
        return "HELLO";
    }

    @GetMapping("/data")
    public ResponseEntity<User> getUserData(OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        System.out.println(token);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<User> exchange = restTemplate.exchange("https://eu.battle.net/oauth/userinfo", HttpMethod.GET, httpEntity, User.class);

        return ResponseEntity.ok(exchange.getBody());
    }

}

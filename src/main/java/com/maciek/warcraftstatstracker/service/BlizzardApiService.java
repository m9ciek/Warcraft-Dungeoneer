package com.maciek.warcraftstatstracker.service;

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

    public <T> ResponseEntity<T> getRequestBlizzardApi(String url, Class<T> responseType, OAuth2Authentication oAuth2Authentication) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = addAuthorizationHeader(oAuth2Authentication);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    private HttpEntity addAuthorizationHeader(OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        logger.info(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new HttpEntity(httpHeaders);
    }
}
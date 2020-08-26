package com.maciek.warcraftstatstracker.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WarcraftLogsApiService {

    public String getWarcraftLogsCharacterData(String characterName, String realm) {
        characterName = characterName.toLowerCase().trim();
        realm = realm.toLowerCase().trim().replace(" ", "-");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("https://www.warcraftlogs.com:443/v1/rankings/character/" + characterName + "/" + realm + "/eu?metric=dps&api_key=ca5b3c548fcdecb538db09c58c909bfa",
                HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

}

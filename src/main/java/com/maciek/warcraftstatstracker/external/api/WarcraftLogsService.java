package com.maciek.warcraftstatstracker.external.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class WarcraftLogsService implements ApiService {

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<String> getCharacterData(String characterName, String realm) {
        System.out.println("wcLOGS");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("https://www.warcraftlogs.com:443/v1/rankings/character/" +
                        correctCharacterName(characterName) + "/" +
                        correctRealmName(realm) + "/eu?metric=dps&api_key=ca5b3c548fcdecb538db09c58c909bfa",
                HttpMethod.GET, httpEntity, String.class);
        return CompletableFuture.completedFuture(response.getBody());
    }

    private String correctRealmName(String realmName) {
        return realmName.toLowerCase().trim().replace(" ", "-");
    }

    private String correctCharacterName(String characterName) {
        return characterName.toLowerCase().trim();
    }

}

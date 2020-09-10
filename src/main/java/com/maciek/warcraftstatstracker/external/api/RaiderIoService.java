package com.maciek.warcraftstatstracker.external.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RaiderIoService implements ApiService {

    @Override
    public String getCharacterData(String characterName, String realm) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("https://raider.io/api/v1/characters/profile?region=eu&realm=" +
                        correctRealmName(realm) + "&name=" +
                        correctCharacterName(characterName) + "&fields=mythic_plus_scores_by_season:current,mythic_plus_ranks,mythic_plus_best_runs",
                HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

    private String correctRealmName(String realmName) {
        return realmName.toLowerCase().trim().replace(" ", "-");
    }

    private String correctCharacterName(String characterName) {
        return characterName.toLowerCase().trim();
    }
}

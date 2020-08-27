package com.maciek.warcraftstatstracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.dto.UserCharacterDTO;
import com.maciek.warcraftstatstracker.dto.WowAccountDTO;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.UserProfile;
import com.maciek.warcraftstatstracker.model.WowAccount;
import com.maciek.warcraftstatstracker.service.api.BlizzardApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDataService {

    private final BlizzardApiService blizzardApiService;

    @Autowired
    public UserDataService(BlizzardApiService blizzardApiService) {
        this.blizzardApiService = blizzardApiService;
    }

    public List<UserCharacterDTO> getCharactersForActiveUser(OAuth2Authentication oAuth2Authentication) {
        ResponseEntity<String> response = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/user/wow?namespace=profile-eu&locale=en_US", String.class, oAuth2Authentication);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readValue(response.getBody(), JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        WowAccountDTO wowAccountDTO = new WowAccountDTO();
        List<UserCharacterDTO> userCharacterDTOs = new ArrayList<>();

        JsonNode parent = node.get("wow_accounts").get(0);
        wowAccountDTO.setId(parent.get("id").asLong());

        JsonNode child = parent.get("characters");
        for(int i=0; i<child.size(); i++) {
            JsonNode childField = child.get(i);
            UserCharacterDTO userCharacterDTO = new UserCharacterDTO();

            userCharacterDTO.setName(childField.get("name").asText());
            userCharacterDTO.setRealm(childField.get("realm").get("name").asText());
            userCharacterDTO.setId(childField.get("id").asLong());
            userCharacterDTO.setCharacterClass(childField.get("playable_class").get("name").asText());
            userCharacterDTO.setFaction(childField.get("faction").get("name").asText());
            userCharacterDTOs.add(userCharacterDTO);
        }
        return userCharacterDTOs;
    }

    //Returns characters with provided name from different realms - if exists
    public List<Character> getCharactersForWowProfile(UserProfile userProfile, String characterName) {
        //First element always returns valid WowAccount
        WowAccount wowAccount = userProfile.getWowAccounts().get(0);
        List<Character> characters = wowAccount.getCharacters().stream()
                .filter(e -> e.getName().equals(capitalize(characterName)))
                .collect(Collectors.toList());
        return characters;
    }

    private static String capitalize(String str) {
        if (str == null) return str;
        String capitalized = str.toLowerCase();
        return capitalized.substring(0, 1).toUpperCase() + capitalized.substring(1).trim();
    }
}

package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.dto.UserCharacterDTO;

import java.util.ArrayList;
import java.util.List;

public class UserCharactersMapper {

    public static List<UserCharacterDTO> mapJSONToUserCharacterDTOs(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = objectMapper.readValue(jsonData, JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<UserCharacterDTO> userCharacterDTOs = new ArrayList<>();
        JsonNode parent = node.get("wow_accounts").get(0);

        JsonNode child = parent.get("characters");
        for (int i = 0; i < child.size(); i++) {
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

}

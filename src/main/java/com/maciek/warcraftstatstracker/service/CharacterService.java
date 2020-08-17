package com.maciek.warcraftstatstracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.mapper.CharacterDetailsMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.CharacterDetails;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    public Character getCharacterFromApi(String apiRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(apiRequest, JsonNode.class);

        Character character = new Character();
        character.setId(node.get("id").asLong());
        character.setName(node.get("name").asText());

        CharacterDetails characterDetails = CharacterDetailsMapper.mapJSONToCharacterDetails(apiRequest);
        character.setCharacterDetails(characterDetails);
        return character;
    }
}

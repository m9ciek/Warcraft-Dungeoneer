package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.CharacterDetails;

import java.sql.Timestamp;

public class CharacterDetailsMapper {

    public static CharacterDetails mapJSONToCharacterDetails(String dataJSON) {
        CharacterDetails characterDetails = new CharacterDetails();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node;
        try {
            node = objectMapper.readValue(dataJSON, JsonNode.class);
            populateDetails(node, characterDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characterDetails;
    }

    private static void populateDetails(JsonNode node, CharacterDetails characterDetails) {
        JsonNode child = node.get("realm");
        JsonNode childField = child.get("name");
        characterDetails.setRealm(childField.asText());

        child = node.get("_links");
        childField = child.get("self");
        child = childField.get("href");
        characterDetails.setUrl(child.asText());

        child = node.get("character_class");
        childField = child.get("name");
        characterDetails.setCharacterClass(childField.asText());

        child = node.get("race");
        childField = child.get("name");
        characterDetails.setRace(childField.asText());

        child = node.get("faction");
        childField = child.get("name");
        characterDetails.setFaction(childField.asText());

        child = node.get("active_spec");
        childField = child.get("name");
        characterDetails.setActiveSpec(childField.asText());

        child = node.get("last_login_timestamp");
        characterDetails.setLastLogin(new Timestamp(child.asLong()).toLocalDateTime());

        child = node.get("level");
        characterDetails.setLevel(child.asInt());

        child = node.get("average_item_level");
        characterDetails.setAverageItemLevel(child.asInt());

        //In case of not guild field provided
        child = node.get("guild");
        if(child != null){
            childField = child.get("name");
            characterDetails.setGuild(childField.asText());
        }
    }

}

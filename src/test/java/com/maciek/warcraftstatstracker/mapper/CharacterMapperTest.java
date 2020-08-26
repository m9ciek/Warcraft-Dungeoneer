package com.maciek.warcraftstatstracker.mapper;

import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.CharacterDetails;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterMapperTest {

    private String responseFromApi;

    @BeforeEach
    void setUp() {
        responseFromApi = "{\"_links\":{\"self\":{\"href\":\"https://api-profile\"}}," +
                "\"id\":130373741,\"name\":\"Druiss\",\"race\":{\"name\":\"Troll\"},\"character_class\":{\"name\":\"Druid\"}," +
                "\"faction\":{\"name\":\"Horde\"},\"active_spec\":{\"name\":\"Balance\"},\"realm\":{\"name\":\"Tarren Mill\"}," +
                "\"last_login_timestamp\":1597309230000,\"average_item_level\":478,\"level\":120}}";
    }

    @Test
    void should_correctly_map_JSON_to_character() {
        //test checks only crucial data
        LocalDateTime loginTime = LocalDateTime.of(2020, 8, 13, 11, 0, 30);
        CharacterDetails characterDetails = new CharacterDetails(120, "Horde", "Troll", "Balance", null, 478, loginTime, new RaiderIOStats(), Collections.emptyList());
        Character character = new Character(130373741, "Druiss", "Druid", "Tarren Mill", "https://api-profile", characterDetails);

        Character mappedCharacter = CharacterMapper.mapJSONToCharacter(responseFromApi);

        assertAll(
                () -> assertEquals(character.getId(), mappedCharacter.getId()),
                () -> assertEquals(loginTime, mappedCharacter.getCharacterDetails().getLastLogin()),
                () -> assertEquals(character.getName(), mappedCharacter.getName()),
                () -> assertEquals(character.getCharacterDetails().getGuild(), mappedCharacter.getCharacterDetails().getGuild()),
                () -> assertEquals(character.getCharacterDetails().getAverageItemLevel(), mappedCharacter.getCharacterDetails().getAverageItemLevel())
        );
    }
}
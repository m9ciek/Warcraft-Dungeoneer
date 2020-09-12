package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.CharacterDetails;
import com.maciek.warcraftstatstracker.model.UserProfile;
import com.maciek.warcraftstatstracker.model.WowAccount;
import com.maciek.warcraftstatstracker.service.user.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserDataServiceTest {

    @Autowired
    private UserDataService userDataService;

    private UserProfile userProfile;


    @BeforeEach
    void setUp() {
        Character character1 = new Character(1, "John", "Mage", "Tarren-mill", "http://url/data", "", new CharacterDetails());
        Character character2 = new Character(2, "Mia", "Warrior", "Stormreaver", "http://url/data1", "", new CharacterDetails());
        Character character3 = new Character(3, "John", "Mage", "Stormreaver", "http://url/data2", "", new CharacterDetails());
        List<Character> characters = Arrays.asList(character1, character2, character3);
        WowAccount wowAccount = new WowAccount(1, characters);
        userProfile = new UserProfile(1, Collections.singletonList(wowAccount));
    }

    @Test
    void should_return_characters_for_given_characterName() {
        assertAll(
                () -> assertEquals(2, userDataService.getCharactersForWowProfile(userProfile, "John").size()),
                () -> assertEquals(1, userDataService.getCharactersForWowProfile(userProfile, "Mia").size()),
                () -> assertEquals("Mia", userDataService.getCharactersForWowProfile(userProfile, "Mia").get(0).getName())
        );
    }

    @Test
    void should_return_empty_array_when_not_found() {
        assertEquals(Collections.EMPTY_LIST, userDataService.getCharactersForWowProfile(userProfile, "dasjify8932dj"));
    }

    @Test
    void should_capitalize_and_return_with_correct_name() {
        assertEquals("John", userDataService.getCharactersForWowProfile(userProfile, "joHn").get(0).getName());
    }
}
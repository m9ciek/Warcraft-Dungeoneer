package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.WowAccount;
import com.maciek.warcraftstatstracker.model.WowProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackerService {

    //Returns characters with provided name from different realms - if exists
    public List<Character> getCharactersForWowProfile(WowProfile wowProfile, String characterName) {
        //First element always returns valid WowAccount
        WowAccount wowAccount = wowProfile.getWowAccounts().get(0);
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

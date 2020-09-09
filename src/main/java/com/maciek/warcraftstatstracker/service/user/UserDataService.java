package com.maciek.warcraftstatstracker.service.user;

import com.maciek.warcraftstatstracker.dto.UserCharacterDTO;
import com.maciek.warcraftstatstracker.mapper.UserCharactersMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.UserProfile;
import com.maciek.warcraftstatstracker.model.WowAccount;
import com.maciek.warcraftstatstracker.external.api.BlizzardApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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
        return UserCharactersMapper.mapJSONToUserCharacterDTOs(response.getBody());
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

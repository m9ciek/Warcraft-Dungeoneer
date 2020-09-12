package com.maciek.warcraftstatstracker.service.character;

import com.maciek.warcraftstatstracker.external.api.ApiService;
import com.maciek.warcraftstatstracker.external.api.BlizzardApiService;
import com.maciek.warcraftstatstracker.external.api.RaiderIoService;
import com.maciek.warcraftstatstracker.external.api.WarcraftLogsService;
import com.maciek.warcraftstatstracker.mapper.CharacterMapper;
import com.maciek.warcraftstatstracker.mapper.RaiderIODataMapper;
import com.maciek.warcraftstatstracker.mapper.WarcraftLogsDataMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;
import com.maciek.warcraftstatstracker.model.WarcraftLogsStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final ApiService raiderIoApiService;
    private final ApiService warcraftLogsApiService;
    private final BlizzardApiService blizzardApiService;

    @Autowired
    public CharacterService(RaiderIoService raiderIoApiService, WarcraftLogsService warcraftLogsApiService, BlizzardApiService blizzardApiService) {
        this.raiderIoApiService = raiderIoApiService;
        this.warcraftLogsApiService = warcraftLogsApiService;
        this.blizzardApiService = blizzardApiService;
    }

    public Character getCharacterFromApi(String name, String realm, OAuth2Authentication oAuth2Authentication) {
        String characterDataString = blizzardApiService.getCharacterData(name, realm, oAuth2Authentication);
        Character character = constructCharacter(characterDataString);
//        character.setRenderImageUrl(getCharacterRenderImageUrl(character, oAuth2Authentication));
        return character;
    }

    public String getCharacterRenderImageUrl(Character character, OAuth2Authentication oAuth2Authentication) {
        String realmSlug = character.getRealm().replace(" ", "-").toLowerCase().trim();
        ResponseEntity<String> characterRenderResponse = blizzardApiService.getRequestBlizzardApi("https://eu.api.blizzard.com/profile/wow/character/" +
                realmSlug + "/" + character.getName().toLowerCase() + "/character-media?namespace=profile-eu&locale=en_GB", String.class, oAuth2Authentication);
        return CharacterMapper.extractCharacterRenderImageUrl(characterRenderResponse.getBody());
    }

    private Character constructCharacter(String characterDataInJson) {
        Character character = CharacterMapper.mapJSONToCharacter(characterDataInJson);
        character.getCharacterDetails().setRaiderIOStats(getRaiderIOStatsForCharacter(character));
        character.getCharacterDetails().setWarcraftLogsStats(getWarcraftLogsStatsForCharacter(character));
        return character;
    }

    private RaiderIOStats getRaiderIOStatsForCharacter(Character character) {
        String raiderIOResponse = raiderIoApiService.getCharacterData(character.getName(), character.getRealm());
        return RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
    }

    private List<WarcraftLogsStats> getWarcraftLogsStatsForCharacter(Character character) {
        String warcraftLogsResponse = warcraftLogsApiService.getCharacterData(character.getName(), character.getRealm());
        return WarcraftLogsDataMapper.mapJSONToWarcraftLogsStats(warcraftLogsResponse);
    }
}

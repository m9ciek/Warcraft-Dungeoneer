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
        String apiResponse = blizzardApiService.getCharacterData(name, realm, oAuth2Authentication);
        Character character = CharacterMapper.mapJSONToCharacter(apiResponse);

        String raiderIOResponse = raiderIoApiService.getCharacterData(character.getName(), character.getRealm());
        String warcraftLogsResponse = warcraftLogsApiService.getCharacterData(character.getName(), character.getRealm());

        RaiderIOStats raiderIOStats = RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
        List<WarcraftLogsStats> warcraftLogsStats = WarcraftLogsDataMapper.mapJSONToWarcraftLogsStats(warcraftLogsResponse);

        character.getCharacterDetails().setRaiderIOStats(raiderIOStats);
        character.getCharacterDetails().setWarcraftLogsStats(warcraftLogsStats);
        return character;
    }
}

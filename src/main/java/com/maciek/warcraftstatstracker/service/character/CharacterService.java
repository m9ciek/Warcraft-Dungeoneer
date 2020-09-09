package com.maciek.warcraftstatstracker.service.character;

import com.maciek.warcraftstatstracker.mapper.CharacterMapper;
import com.maciek.warcraftstatstracker.mapper.RaiderIODataMapper;
import com.maciek.warcraftstatstracker.mapper.WarcraftLogsDataMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;
import com.maciek.warcraftstatstracker.model.WarcraftLogsStats;
import com.maciek.warcraftstatstracker.external.api.ApiService;
import com.maciek.warcraftstatstracker.external.api.RaiderIoService;
import com.maciek.warcraftstatstracker.external.api.WarcraftLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final ApiService raiderIoApiService;
    private final ApiService warcraftLogsApiService;

    @Autowired
    public CharacterService(RaiderIoService raiderIoService, WarcraftLogsService warcraftLogsService) {
        this.raiderIoApiService = raiderIoService;
        this.warcraftLogsApiService = warcraftLogsService;
    }

    public Character getCharacterFromApi(String apiRequest) {
        Character character = CharacterMapper.mapJSONToCharacter(apiRequest);
        String raiderIOResponse = raiderIoApiService.getCharacterData(character.getName(), character.getRealm());
        String warcraftLogsResponse = warcraftLogsApiService.getCharacterData(character.getName(), character.getRealm());

        RaiderIOStats raiderIOStats = RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
        List<WarcraftLogsStats> warcraftLogsStats = WarcraftLogsDataMapper.mapJSONToWarcraftLogsStats(warcraftLogsResponse);

        character.getCharacterDetails().setRaiderIOStats(raiderIOStats);
        character.getCharacterDetails().setWarcraftLogsStats(warcraftLogsStats);
        return character;
    }
}

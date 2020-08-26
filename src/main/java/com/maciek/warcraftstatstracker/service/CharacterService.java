package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.mapper.CharacterMapper;
import com.maciek.warcraftstatstracker.mapper.RaiderIODataMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    private RaiderIoApiService raiderIoApiService;
    private WarcraftLogsApiService warcraftLogsApiService;

    @Autowired
    public CharacterService(RaiderIoApiService raiderIoApiService, WarcraftLogsApiService warcraftLogsApiService) {
        this.raiderIoApiService = raiderIoApiService;
        this.warcraftLogsApiService = warcraftLogsApiService;
    }

    public Character getCharacterFromApi(String apiRequest) {
        Character character = CharacterMapper.mapJSONToCharacter(apiRequest);
        String raiderIOResponse = raiderIoApiService.getRaiderIOCharacterData(character.getName(), character.getRealm());
        RaiderIOStats raiderIOStats = RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
        character.getCharacterDetails().setRaiderIOStats(raiderIOStats);
        return character;
    }
}

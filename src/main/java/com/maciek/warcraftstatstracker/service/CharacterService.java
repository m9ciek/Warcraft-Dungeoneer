package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.mapper.CharacterMapper;
import com.maciek.warcraftstatstracker.mapper.RaiderIODataMapper;
import com.maciek.warcraftstatstracker.mapper.WarcraftLogsDataMapper;
import com.maciek.warcraftstatstracker.model.Character;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;
import com.maciek.warcraftstatstracker.model.WarcraftLogsStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        String warcraftLogsResponse = warcraftLogsApiService.getWarcraftLogsCharacterData(character.getName(), character.getRealm());

        RaiderIOStats raiderIOStats = RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
        List<WarcraftLogsStats> warcraftLogsStats = WarcraftLogsDataMapper.mapJSONToWarcraftLogsStats(warcraftLogsResponse);

        character.getCharacterDetails().setRaiderIOStats(raiderIOStats);
        return character;
    }
}

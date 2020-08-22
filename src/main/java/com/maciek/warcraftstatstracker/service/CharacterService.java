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

    @Autowired
    public CharacterService(RaiderIoApiService raiderIoApiService) {
        this.raiderIoApiService = raiderIoApiService;
    }

    public Character getCharacterFromApi(String apiRequest) {
        Character character = CharacterMapper.mapJSONToCharacter(apiRequest);
        String raiderIOResponse = raiderIoApiService.getRaiderIOApi(character.getName(), character.getRealm());
        RaiderIOStats raiderIOStats = RaiderIODataMapper.mapJSONToRaiderIOStats(raiderIOResponse);
        character.getCharacterDetails().setRaiderIOStats(raiderIOStats);
        return character;
    }
}

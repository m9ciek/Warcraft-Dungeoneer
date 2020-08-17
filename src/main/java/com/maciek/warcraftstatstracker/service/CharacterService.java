package com.maciek.warcraftstatstracker.service;

import com.maciek.warcraftstatstracker.mapper.CharacterMapper;
import com.maciek.warcraftstatstracker.model.Character;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    public Character getCharacterFromApi(String apiRequest) {
        return CharacterMapper.mapJSONToCharacter(apiRequest);
    }
}

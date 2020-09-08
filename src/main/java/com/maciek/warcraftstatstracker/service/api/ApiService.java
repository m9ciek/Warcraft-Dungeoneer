package com.maciek.warcraftstatstracker.service.api;

import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    String getCharacterData(String characterName, String realm);
}

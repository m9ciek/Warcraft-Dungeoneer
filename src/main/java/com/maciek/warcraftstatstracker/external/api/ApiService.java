package com.maciek.warcraftstatstracker.external.api;

import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    String getCharacterData(String characterName, String realm);
}

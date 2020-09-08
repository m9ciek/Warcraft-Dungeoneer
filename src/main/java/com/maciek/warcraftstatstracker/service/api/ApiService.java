package com.maciek.warcraftstatstracker.service.api;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    String getCharacterData(String characterName, String realm);

    void authenticateOAuth2(OAuth2Authentication oAuth2Authentication);
}

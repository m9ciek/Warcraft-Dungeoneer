package com.maciek.warcraftstatstracker.external.api;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface ApiService {
    CompletableFuture<String> getCharacterData(String characterName, String realm);
}

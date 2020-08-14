package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WowAccount {
    @JsonProperty("id")
    private long id;
    @JsonProperty("characters")
    private List<Character> characters;
}

package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterDetails {
    @JsonProperty("href")
    private String url;

    private String realm;
    private int level;
    private String faction;
    private String race;
    private String characterClass;
    private String activeSpec;
    private String guild;

}

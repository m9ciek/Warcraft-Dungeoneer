package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDetails {

    private int level;
    private String faction;
    private String race;

    @JsonProperty("active-spec")
    private String activeSpec;
    private String guild;

    @JsonProperty("average-item-level")
    private int averageItemLevel;

    @JsonProperty("last-login")
    private LocalDateTime lastLogin;

    @JsonProperty("raiderIO-stats")
    private RaiderIOStats raiderIOStats;
}

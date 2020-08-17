package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

}

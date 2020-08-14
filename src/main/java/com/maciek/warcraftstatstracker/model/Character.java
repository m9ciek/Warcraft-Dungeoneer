package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "id",
        "name"
})
public class Character {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonAlias({"character"})
    private CharacterDetails characterDetails;

    @JsonGetter("character-details")
    public CharacterDetails getCharacterDetails() {
        return characterDetails;
    }
}

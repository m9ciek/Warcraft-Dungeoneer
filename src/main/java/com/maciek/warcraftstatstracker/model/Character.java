package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "id",
        "name",
        "character-class"
})
public class Character {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("character-class")
    private String characterClass;
    private String realm;
    private String url;

    @JsonAlias({"character"})
    private CharacterDetails characterDetails;

    @JsonGetter("character-details")
    public CharacterDetails getCharacterDetails() {
        return characterDetails;
    }
}

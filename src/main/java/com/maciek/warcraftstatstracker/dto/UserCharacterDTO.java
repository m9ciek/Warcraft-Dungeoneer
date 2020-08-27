package com.maciek.warcraftstatstracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
        "id",
        "name",
        "character-class"
})
public class UserCharacterDTO {
    private long id;
    private String name;
    private String realm;

    @JsonProperty("character-class")
    private String characterClass;
    private String faction;
}

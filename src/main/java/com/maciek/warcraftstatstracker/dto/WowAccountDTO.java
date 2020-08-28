package com.maciek.warcraftstatstracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WowAccountDTO {
    private long id;
    @JsonProperty("characters")
    private List<UserCharacterDTO> userCharacters;
}

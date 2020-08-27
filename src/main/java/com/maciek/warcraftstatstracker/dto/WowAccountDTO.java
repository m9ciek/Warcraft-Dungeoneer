package com.maciek.warcraftstatstracker.dto;

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
    private List<UserCharacterDTO> userCharacters;
}

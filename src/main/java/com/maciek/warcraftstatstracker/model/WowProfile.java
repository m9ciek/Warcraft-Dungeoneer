package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WowProfile {
    @JsonProperty("id")
    private long id;
    @JsonProperty("wow_accounts")
    private List<WowAccount> wowAccounts;

}

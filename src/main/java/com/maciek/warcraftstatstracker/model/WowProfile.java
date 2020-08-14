package com.maciek.warcraftstatstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WowProfile {
    @JsonProperty("id")
    private long id;
    @JsonProperty("wow_accounts")
    private List<WowAccount> wowAccounts;

}

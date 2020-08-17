package com.maciek.warcraftstatstracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MythicPlusStats {

    private double overallScore;
    private int worldRank;
    private int realmRank;

}

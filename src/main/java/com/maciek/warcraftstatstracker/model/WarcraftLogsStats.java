package com.maciek.warcraftstatstracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarcraftLogsStats {
    private int encounterId;
    private String encounterName;
    private String reportId;
    private Difficulty difficulty;
    private double percentile;

    public void setDifficulty(int value) {
        if(value == 5){
            this.difficulty = Difficulty.MYTHIC;
        } else if (value == 4) {
            this.difficulty = Difficulty.HEROIC;
        } else if (value == 3) {
            this.difficulty = Difficulty.NORMAL;
        } else {
            this.difficulty = Difficulty.LFR;
        }
    }

}

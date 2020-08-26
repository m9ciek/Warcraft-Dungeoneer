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
    private int difficulty;
    private double percentile;
}

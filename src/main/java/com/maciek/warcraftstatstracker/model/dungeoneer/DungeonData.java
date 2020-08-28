package com.maciek.warcraftstatstracker.model.dungeoneer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DungeonData {
    private int season;
    private List<MythicPlusDungeon> mythicPlusDungeons;
}

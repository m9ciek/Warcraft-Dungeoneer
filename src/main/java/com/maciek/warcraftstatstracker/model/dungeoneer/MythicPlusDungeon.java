package com.maciek.warcraftstatstracker.model.dungeoneer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MythicPlusDungeon {
    private int id;
    private String name;
    private int keystoneLevel;
    private LocalDateTime completedDate;
    private LocalTime duration;
    private LocalTime timer;
    private Set<KeystoneAffix> affixes;
    private boolean isCompletedWithinTime;
    private double score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MythicPlusDungeon that = (MythicPlusDungeon) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

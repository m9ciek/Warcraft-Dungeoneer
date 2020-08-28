package com.maciek.warcraftstatstracker.model.dungeoneer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeystoneAffix {
    private int id;
    private String name;
    private String href;
}

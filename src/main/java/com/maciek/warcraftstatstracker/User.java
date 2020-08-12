package com.maciek.warcraftstatstracker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("id")
    private long id;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("battletag")
    private String battleTag;

    public User(long id, String sub, String battleTag) {
        this.id = id;
        this.sub = sub;
        this.battleTag = battleTag;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }
}

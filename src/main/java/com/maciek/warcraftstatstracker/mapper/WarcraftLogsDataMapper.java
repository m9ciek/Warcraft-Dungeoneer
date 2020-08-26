package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.WarcraftLogsStats;

public class WarcraftLogsDataMapper {

    public static WarcraftLogsStats mapJSONToRaiderIOStats(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        WarcraftLogsStats warcraftLogsStats = new WarcraftLogsStats();
        JsonNode node;
        try {
            node = mapper.readValue(jsonData, JsonNode.class);
            populateWarcraftLogsStats(node, warcraftLogsStats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return warcraftLogsStats;
    }

    private static void populateWarcraftLogsStats(JsonNode node, WarcraftLogsStats warcraftLogsStatsToPopulate) {
        JsonNode parent = node.get("mythic_plus_scores_by_season");
        JsonNode child = parent.get(0).get("scores");
        JsonNode childField = child.get("all");
//        warcraftLogsStatsToPopulate.setOverallScore(childField.asDouble());

        parent = node.get("mythic_plus_ranks");
        child = parent.get("overall");
        childField = child.get("world");
//        warcraftLogsStatsToPopulate.setWorldRank(childField.asInt());

        childField = child.get("realm");
//        warcraftLogsStatsToPopulate.setRealmRank(childField.asInt());
    }
}

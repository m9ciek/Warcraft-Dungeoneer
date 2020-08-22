package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.RaiderIOStats;

public class RaiderIODataMapper {

    public static RaiderIOStats mapJSONToRaiderIOStats(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        RaiderIOStats raiderIOStats = new RaiderIOStats();
        JsonNode node;
        try {
            node = mapper.readValue(jsonData, JsonNode.class);
            populateRaiderIOStats(raiderIOStats, node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return raiderIOStats;
    }

    private static void populateRaiderIOStats(RaiderIOStats raiderIOStatsToPopulate, JsonNode node) {
        JsonNode parent = node.get("mythic_plus_scores_by_season");
        JsonNode child = parent.get(0).get("scores");
        JsonNode childField = child.get("all");
        raiderIOStatsToPopulate.setOverallScore(childField.asDouble());

        parent = node.get("mythic_plus_ranks");
        child = parent.get("overall");
        childField = child.get("world");
        raiderIOStatsToPopulate.setWorldRank(childField.asInt());

        childField = child.get("realm");
        raiderIOStatsToPopulate.setRealmRank(childField.asInt());
    }
}

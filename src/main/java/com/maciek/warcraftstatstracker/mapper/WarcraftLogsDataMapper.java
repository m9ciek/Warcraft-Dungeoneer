package com.maciek.warcraftstatstracker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.warcraftstatstracker.model.WarcraftLogsStats;

import java.util.ArrayList;
import java.util.List;

public class WarcraftLogsDataMapper {

    public static List<WarcraftLogsStats> mapJSONToWarcraftLogsStats(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        List<WarcraftLogsStats> warcraftLogsStats = new ArrayList<>();
        JsonNode node;
        try {
            node = mapper.readValue(jsonData, JsonNode.class);
            for (int i = 0; i < node.size(); i++) {
                WarcraftLogsStats singleWarcraftLogsStat = new WarcraftLogsStats();
                populateWarcraftLogsStats(node, singleWarcraftLogsStat, i);
                warcraftLogsStats.add(singleWarcraftLogsStat);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return warcraftLogsStats;
    }

    private static void populateWarcraftLogsStats(JsonNode node, WarcraftLogsStats warcraftLogsStatsToPopulate, int elementNumber) {
        JsonNode parent = node.get(elementNumber);

        JsonNode child = parent.get("encounterID");
        warcraftLogsStatsToPopulate.setEncounterId(child.asInt());

        child = parent.get("encounterName");
        warcraftLogsStatsToPopulate.setEncounterName(child.asText());

        child = parent.get("reportID");
        warcraftLogsStatsToPopulate.setReportId(child.asText());

        child = parent.get("difficulty");
        warcraftLogsStatsToPopulate.setDifficulty(child.asInt());

        child = parent.get("percentile");
        warcraftLogsStatsToPopulate.setPercentile(Math.round(child.asDouble() * 100.0) / 100.0); //rounded up to 2 decimal places
    }

}

//package com.maciek.warcraftstatstracker.controller;
//
//import com.maciek.warcraftstatstracker.exception.advice.CharacterControllerAdvice;
//import com.maciek.warcraftstatstracker.model.Character;
//import com.maciek.warcraftstatstracker.model.*;
//import com.maciek.warcraftstatstracker.service.character.CharacterService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class CharacterControllerTest {
//
//    @Mock
//    private CharacterService characterServiceMock;
//
//    private MockMvc mockMvc;
//
//    private Character character;
//
//    @BeforeEach
//    void setUp() {
//        CharacterController characterController = new CharacterController(characterServiceMock);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(characterController)
//                .setControllerAdvice(new CharacterControllerAdvice())
//                .build();
//
//        character = new Character();
//        character.setId(11);
//        character.setName("Faravir");
//        character.setCharacterClass("Warlock");
//        character.setRealm("Tarren Mill");
//        character.setUrl("http://website.url");
//
//        CharacterDetails characterDetails = new CharacterDetails();
//        characterDetails.setLevel(120);
//        characterDetails.setFaction("Horde");
//        characterDetails.setRace("Orc");
//        characterDetails.setGuild("Random");
//        characterDetails.setAverageItemLevel(444);
//        characterDetails.setLastLogin(LocalDateTime.of(2020, 8, 12, 12, 23));
//
//        RaiderIOStats raiderIOStats = new RaiderIOStats(2000.0, 150, 1234);
//        characterDetails.setRaiderIOStats(raiderIOStats);
//
//        WarcraftLogsStats warcraftLogsStats = new WarcraftLogsStats(22, "Boss", "ABCD123", Difficulty.HEROIC, 88.9);
//        characterDetails.setWarcraftLogsStats(Collections.singletonList(warcraftLogsStats));
//        character.setCharacterDetails(characterDetails);
//
//        given(this.characterServiceMock.getCharacterFromApi(anyString(), anyString(), any())).willReturn(this.character);
//
//
//    }
//
//    @Test
//    void test_get_valid_character() throws Exception {
//        mockMvc.perform(get("/api/character/faravir").param("realm", "tarren-mill"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Faravir"))
//                .andExpect(jsonPath("$.realm").value("Tarren Mill"))
//                .andExpect(jsonPath("$.id").value(11))
//                .andExpect(jsonPath("$.character-details").exists())
//                .andExpect(jsonPath("$.character-details.raiderIO-stats").exists())
//                .andExpect(jsonPath("$.character-details.warcraft-logs-stats").exists());
//    }
//
//    @Test
//    void test_get_invalid_character() throws Exception {
//        given(this.characterServiceMock.getCharacterFromApi("invalid", "invalid", null)).willThrow(HttpClientErrorException.class);
//        mockMvc.perform(get("/api/character/invalid").param("realm", "invalid"))
//                .andExpect(status().isNotFound());
//
//    }
//
//    @Test
//    void test_get_characterDetails() throws Exception {
//        mockMvc.perform(get("/api/character/faravir").param("realm", "tarren-mill"))
//                .andExpect(jsonPath("$.character-details.level").value(120))
//                .andExpect(jsonPath("$.character-details.race").value("Orc"));
//    }
//
//    @Test
//    void test_get_raiderIoData() throws Exception {
//        mockMvc.perform(get("/api/character/faravir").param("realm", "tarren-mill"))
//                .andExpect(jsonPath("$.character-details.raiderIO-stats.overallScore").value(2000.0));
//    }
//
//    @Test
//    void test_get_WarcraftLogsData() throws Exception {
//        mockMvc.perform(get("/api/character/faravir").param("realm", "tarren-mill"))
//                .andExpect(jsonPath("$.character-details.warcraft-logs-stats[0].encounterName").value("Boss"));
//    }
//}
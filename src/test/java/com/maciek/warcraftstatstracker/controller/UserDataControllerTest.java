package com.maciek.warcraftstatstracker.controller;

import com.maciek.warcraftstatstracker.dto.UserCharacterDTO;
import com.maciek.warcraftstatstracker.external.api.BlizzardApiService;
import com.maciek.warcraftstatstracker.model.User;
import com.maciek.warcraftstatstracker.service.user.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserDataControllerTest {

    @Mock
    private BlizzardApiService blizzardApiServiceMock;
    @Mock
    private UserDataService userDataServiceMock;

    private MockMvc mockMvc;

    private User user;
    private List<UserCharacterDTO> userCharacterDTOList;

    @BeforeEach
    void setUp() {
        UserDataController userDataController = new UserDataController(blizzardApiServiceMock, userDataServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userDataController).build();

        user = new User(5555, "5555", "user#5555");
        UserCharacterDTO userCharacterDTOOne = UserCharacterDTO.builder()
                .id(1)
                .faction("Horde")
                .characterClass("Shaman")
                .name("John")
                .realm("Blue").build();
        UserCharacterDTO userCharacterDTOTwo = UserCharacterDTO.builder()
                .id(2)
                .realm("Red")
                .name("Karen")
                .characterClass("Warrior")
                .faction("Alliance").build();
        userCharacterDTOList = new ArrayList<>();
        userCharacterDTOList.add(userCharacterDTOOne);
        userCharacterDTOList.add(userCharacterDTOTwo);
    }

    @Test
    void test_get_userData() throws Exception {
        given(blizzardApiServiceMock.getRequestBlizzardApi(anyString(), any(), any())).willReturn(ResponseEntity.ok(this.user));
        mockMvc.perform(get("/api/user-data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5555))
                .andExpect(jsonPath("$.sub").value("5555"))
                .andExpect(jsonPath("$.battletag").value("user#5555"));
    }

    @Test
    void test_get_wowData() throws Exception {
        given(blizzardApiServiceMock.getRequestBlizzardApi(anyString(), any(), any())).willReturn(ResponseEntity.ok("Json User Data"));
        mockMvc.perform(get("/api/user-data/wow-profile"))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_list_of_user_characters() throws Exception {
        given(userDataServiceMock.getCharactersForActiveUser(any())).willReturn(this.userCharacterDTOList);
        mockMvc.perform(get("/api/user-data/user-characters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[1].id").value(2));
    }
}
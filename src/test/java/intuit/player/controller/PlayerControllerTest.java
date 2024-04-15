package intuit.player.controller;

import intuit.player.model.Player;
import intuit.player.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        // Setup your test data here
    }

    @Test
    void getAllPlayers() throws Exception {
        given(playerService.getAllPlayers()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getPlayerByIdSuccess() throws Exception {
        Player mockPlayer = new Player(1L,1,"Test Player", 25, "Test Team", "Forward");
        given(playerService.getPlayerById(1)).willReturn(mockPlayer);

        mockMvc.perform(get("/api/v1/players/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Player"));
    }
}

package intuit.player.service;

import intuit.player.exception.PlayerNotFoundException;
import intuit.player.model.Player;
import intuit.player.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void getPlayerByIdFound() {
        Player mockPlayer = new Player("Test Player", 25, "Test Team", "Forward");
        when(playerRepository.findById(1)).thenReturn(Optional.of(mockPlayer));

        Player found = playerService.getPlayerById(1);

        assertNotNull(found);
        assertEquals("Test Player", found.getName());
    }

    @Test
    void getPlayerByIdNotFound() {
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PlayerNotFoundException.class, () ->
                playerService.getPlayerById(999));

        assertTrue(exception.getMessage().contains("Player not found with id: 999"));
    }
}

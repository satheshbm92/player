package intuit.player.controller;

import intuit.player.model.Player;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import intuit.player.service.PlayerService;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;


    @GetMapping("api/v1/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("api/v1/players/{id}")
    public Player getPlayerById(@PathVariable Integer id) {
        return playerService.getPlayerById(id);
    }

}

package intuit.player.controller;

import intuit.player.model.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import intuit.player.service.PlayerService;

import java.util.List;

@RestController
@Validated
public class PlayerController {

    private PlayerService playerService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);


    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Get a list of all players")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of players",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("api/v1/players")
    public List<Player> getAllPlayers() {
        logger.info("Fetching all players");
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Get a player by ID", tags = {"Player"})
    @ApiResponse(responseCode = "200", description = "Player found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class),
                    examples = @ExampleObject(value = "{'id': 1, 'name': 'John Doe', 'age': 25, 'team': 'Warriors', 'position': 'Forward'}")))
    @ApiResponse(responseCode = "404", description = "Player not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/api/v1/players/id/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable @Min(1) Integer id) {
        logger.info("Fetching player with ID: {}", id);
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @Operation(summary = "Get players by name prefix")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved players with the specified name prefix",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class),
                    examples = @ExampleObject(value = "[{'id': 2, 'name': 'Jane Doe', 'age': 22, 'team': 'Warriors', 'position': 'Forward'}]")))
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/api/v1/players/prefix/{prefix}")
    public ResponseEntity<List<Player>> getPlayersByPrefix(@PathVariable String prefix) {
        logger.info("Fetching players with name prefix: {}", prefix);
        List<Player> players = playerService.getPlayersByPrefix(prefix);
        return ResponseEntity.ok(players);
    }
}

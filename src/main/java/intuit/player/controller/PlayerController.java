package intuit.player.controller;

import intuit.player.model.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import intuit.player.service.PlayerService;

import java.util.List;

@RestController
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Get a list of all players")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of players",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class),
                    examples = {
                            @ExampleObject(name="example1", summary="Standard example", value = "[{'id': 1, 'name': 'John Doe', 'team': 'Warriors'}]"),
                            @ExampleObject(name="example2", summary="Empty list", value = "[]")
                    }))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{'status': 500, 'message': 'Unable to retrieve player data'}")))
    @GetMapping("api/v1/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Get a player by ID", tags = {"Player"})
    @ApiResponse(responseCode = "200", description = "Successfully retrieved player",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Player.class),
                    examples = @ExampleObject(value = "{'id': 1, 'name': 'John Doe', 'team': 'Warriors'}")))
    @ApiResponse(responseCode = "404", description = "Player not found",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{'status': 404, 'message': 'Player not found with ID: 1'}")))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{'status': 500, 'message': 'Internal server error while fetching player details'}")))
    @GetMapping("api/v1/players/{id}")
    public Player getPlayerById(@PathVariable Integer id) {
        return playerService.getPlayerById(id);
    }

}

package intuit.player.service;

import intuit.player.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();
    Player getPlayerById(Integer id);
    List<Player> getPlayersByPrefix(String prefix);
}

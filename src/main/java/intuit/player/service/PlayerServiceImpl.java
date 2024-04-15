package intuit.player.service;

import intuit.player.exception.PlayerNotFoundException;
import intuit.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import intuit.player.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
    }

    @Override
    public List<Player> getPlayersByPrefix(String prefix) {
        return playerRepository.findByNameStartingWith(prefix);
    }

}

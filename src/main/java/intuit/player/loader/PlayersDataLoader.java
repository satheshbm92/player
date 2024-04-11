package intuit.player.loader;

import intuit.player.model.Player;
import intuit.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class PlayersDataLoader implements CommandLineRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:players.csv");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().skip(1).forEach(line -> {
                String[] details = line.split(",");
                Integer playerId = Integer.valueOf(details[0]);
                // Check if the player already exists
                if (!playerRepository.findByPlayerId(playerId).isPresent()) {
                    Player player = new Player();
                    player.setPlayerId(playerId); // Set the unique ID from the CSV
                    player.setName(details[1]);
                    player.setAge(Integer.parseInt(details[2]));
                    player.setTeam(details[3]);
                    player.setPosition(details[4]);
                    playerRepository.save(player);
                }
            });
        }
    }

}

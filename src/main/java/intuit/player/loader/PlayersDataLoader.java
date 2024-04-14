package intuit.player.loader;

import intuit.player.model.Player;
import intuit.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayersDataLoader implements CommandLineRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final int BATCH_SIZE = 50; // Adjust this size based on your database and application server capacity

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:players.csv");
        List<Player> playersToSave = new ArrayList<>();

        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                try {
                    Integer playerId = Integer.parseInt(nextRecord[0]);
                    // Check if the player already exists
                    if (!playerRepository.findByPlayerId(playerId).isPresent()) {
                        Player player = new Player();
                        player.setPlayerId(playerId);
                        player.setName(nextRecord[1]);
                        player.setAge(Integer.parseInt(nextRecord[2]));
                        player.setTeam(nextRecord[3]);
                        player.setPosition(nextRecord[4]);
                        playersToSave.add(player);

                        // Batch save in chunks
                        if (playersToSave.size() >= BATCH_SIZE) {
                            playerRepository.saveAll(playersToSave);
                            playersToSave.clear();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Failed to process record: " + e.getMessage());
                }
            }

            // Save any remaining players not fitting exactly into the batch size
            if (!playersToSave.isEmpty()) {
                playerRepository.saveAll(playersToSave);
            }
        }
    }
}

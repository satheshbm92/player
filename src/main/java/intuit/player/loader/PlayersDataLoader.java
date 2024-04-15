package intuit.player.loader;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import intuit.player.model.Player;
import intuit.player.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PlayersDataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PlayersDataLoader.class);
    private static final int BATCH_SIZE = 50;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${app.players-data-file}")
    private String playersDataFilePath;

    @Override
    public void run(String... args) {
        logger.info("Starting player data loading process...");
        Set<Integer> existingPlayerIds = getExistingPlayerIds();
        int newPlayersCount = loadPlayersFromCsv(playersDataFilePath, existingPlayerIds);
        logger.info("Player data loading process completed. {} new players were added.", newPlayersCount);
    }

    private Set<Integer> getExistingPlayerIds() {
        return new HashSet<>(playerRepository.findAllPlayerIds());
    }

    @Transactional
    protected int loadPlayersFromCsv(String filePath, Set<Integer> existingPlayerIds) {
        int newPlayersCount = 0;
        List<Player> playersToSave = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(resourceLoader.getResource(filePath).getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(isr).withSkipLines(1).build()) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Player player = parsePlayer(nextRecord);
                if (player != null && !existingPlayerIds.contains(player.getPlayerId())) {
                    playersToSave.add(player);
                    newPlayersCount++;
                    if (playersToSave.size() >= BATCH_SIZE) {
                        playerRepository.saveAll(playersToSave);
                        playersToSave.clear();
                    }
                }
            }
            if (!playersToSave.isEmpty()) {
                playerRepository.saveAll(playersToSave);
            }
        } catch (IOException | CsvValidationException e) {
            logger.error("Error reading players data file: {}", e.getMessage());
        }
        return newPlayersCount;
    }

    private Player parsePlayer(String[] record) {
        Player player = new Player();
        player.setPlayerId(Integer.parseInt(record[0]));
        player.setName(record[1]);
        player.setAge(Integer.parseInt(record[2]));
        player.setTeam(record[3]);
        player.setPosition(record[4]);
        return player;
    }
}

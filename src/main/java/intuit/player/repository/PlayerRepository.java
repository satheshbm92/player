package intuit.player.repository;

import intuit.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    // Using LIKE to fetch players whose names start with the specified prefix
    @Query("SELECT p FROM Player p WHERE p.name LIKE ?1%")
    List<Player> findByNameStartingWith(String prefix);

    @Query("SELECT p.playerId FROM Player p")
    List<Integer> findAllPlayerIds();

}

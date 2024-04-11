package intuit.player.repository;

import intuit.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findByPlayerId(Integer playerId);

}

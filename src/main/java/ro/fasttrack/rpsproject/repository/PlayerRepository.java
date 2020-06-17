package ro.fasttrack.rpsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrack.rpsproject.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}

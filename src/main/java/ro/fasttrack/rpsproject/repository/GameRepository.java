package ro.fasttrack.rpsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrack.rpsproject.domain.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
}

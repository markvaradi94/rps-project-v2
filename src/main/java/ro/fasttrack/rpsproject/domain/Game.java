package ro.fasttrack.rpsproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer firstPlayerId;
    private Integer secondPlayerId;
    private String gameUrl;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    public Game() {
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId, GameStatus gameStatus, String gameUrl) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.gameStatus = gameStatus;
        this.gameUrl = gameUrl;
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId, String gameUrl) {
        this(firstPlayerId, secondPlayerId, GameStatus.WAITING_FOR_PLAYER, gameUrl);
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId, GameStatus gameStatus) {
        this(firstPlayerId, secondPlayerId, gameStatus, "");
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId) {
        this(firstPlayerId, secondPlayerId, "");
    }
}

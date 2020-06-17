package ro.fasttrack.rpsproject.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Hand hand;

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    public Player(String username, Hand hand) {
        this.username = username;
        this.hand = hand;
    }

    public Player(String username) {
        this(username, Hand.NONE);
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    public void addDraw() {
        this.draws++;
    }
}

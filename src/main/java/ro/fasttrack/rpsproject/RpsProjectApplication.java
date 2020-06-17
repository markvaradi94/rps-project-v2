package ro.fasttrack.rpsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrack.rpsproject.domain.Game;
import ro.fasttrack.rpsproject.domain.Hand;
import ro.fasttrack.rpsproject.domain.Player;
import ro.fasttrack.rpsproject.service.GameService;
import ro.fasttrack.rpsproject.service.PlayerService;

@SpringBootApplication
public class RpsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpsProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(PlayerService playerService, GameService gameService) {
        return args -> {
            playerService.addPlayer(new Player("Mark"));
            playerService.addPlayer(new Player("Gicu"));

            gameService.addGame(new Game(1, 2));
        };
    }
}

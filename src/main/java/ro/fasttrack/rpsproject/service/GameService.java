package ro.fasttrack.rpsproject.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ro.fasttrack.rpsproject.domain.Game;
import ro.fasttrack.rpsproject.domain.GameStatus;
import ro.fasttrack.rpsproject.domain.Hand;
import ro.fasttrack.rpsproject.domain.Player;
import ro.fasttrack.rpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrack.rpsproject.exceptions.SamePlayerException;
import ro.fasttrack.rpsproject.repository.GameRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    private PlayerService playerService;

    public GameService(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game addGame(Game game) {
        checkIdenticalPlayers(game);
        Game gameWithResult = gameWithResult(game);
        if (gameWithResult.getGameUrl() == null || gameWithResult.getGameUrl().isEmpty()) {
            gameWithResult.setGameUrl(generateRandomUrl());
        }
        return gameRepository.save(gameWithResult);
    }

    public Game getGameById(Integer id) {
        return getOrThrow(id);
    }

    public List<Game> gamesWithPlayerInvolved(Integer id) {
        Player playerToFind = playerService.findPlayerById(id);
        return gameRepository.findAll().stream()
                .filter(game -> gameInvolvesPlayer(game, playerToFind.getId()))
                .collect(Collectors.toList());
    }

    private Game gameWithResult(Game game) {
        Player player1 = playerService.findPlayerById(game.getFirstPlayerId());
        Player player2 = playerService.findPlayerById(game.getSecondPlayerId());
        checkPlayersHands(player1, player2);
        GameStatus status = gameStatus(player1, player2);
        return new Game(player1.getId(), player2.getId(), status);
    }

    private Game getOrThrow(Integer id) {
        return gameRepository.findAll().stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find game with ID " + id));
    }

    private boolean gameInvolvesPlayer(Game game, Integer id) {
        return game.getFirstPlayerId().equals(id) || game.getSecondPlayerId().equals(id);
    }

    private String generateRandomUrl() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void checkPlayersHands(Player player1, Player player2) {
        if (player1.getHand() == null) {
            throw new ResourceNotFoundException("Player with ID " + player1.getId() + " has not selected a Hand.");
        }
        if (player2.getHand() == null) {
            throw new ResourceNotFoundException("Player with ID " + player2.getId() + " has not selected a Hand.");
        }
    }

    private GameStatus gameStatus(Player player1, Player player2) {
        if (player1.getHand().equals(Hand.ROCK) && player2.getHand().equals(Hand.SCISSORS) ||
                player1.getHand().equals(Hand.PAPER) && player2.getHand().equals(Hand.ROCK) ||
                player1.getHand().equals(Hand.SCISSORS) && player2.getHand().equals(Hand.PAPER)) {
            playerService.addWinToPlayer(player1);
            playerService.addLossToPlayer(player2);
            return GameStatus.PLAYER_1_WINS;
        } else if (player1.getHand().equals(player2.getHand()) && !player1.getHand().equals(Hand.NONE) &&
                !player2.getHand().equals(Hand.NONE)) {
            playerService.addDrawToPlayer(player1);
            playerService.addDrawToPlayer(player2);
            return GameStatus.DRAW;
        } else if (player1.getHand().equals(Hand.NONE) || player2.getHand().equals(Hand.NONE)) {
            return GameStatus.WAITING_FOR_PLAYER;
        } else {
            playerService.addWinToPlayer(player2);
            playerService.addLossToPlayer(player1);
            return GameStatus.PLAYER_2_WINS;
        }
    }

    private void checkIdenticalPlayers(Game game) {
        Player player1 = playerService.findPlayerById(game.getFirstPlayerId());
        Player player2 = playerService.findPlayerById(game.getSecondPlayerId());
        if (player1.getId().equals(player2.getId()) || player1.getUsername().equals(player2.getUsername())) {
            throw new SamePlayerException("Cannot create new game with identical players");
        }
    }
}

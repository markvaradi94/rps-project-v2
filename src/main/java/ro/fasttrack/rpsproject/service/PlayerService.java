package ro.fasttrack.rpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrack.rpsproject.domain.Hand;
import ro.fasttrack.rpsproject.domain.Player;
import ro.fasttrack.rpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrack.rpsproject.repository.PlayerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player addPlayer(Player player) {
        Player newPlayer = new Player(player.getUsername(), player.getHand() == null ? Hand.NONE : player.getHand());
        return playerRepository.save(newPlayer);
    }

    public Player findPlayerById(Integer id) {
        return getOrThrow(id);
    }

    public Player findPlayerByUsername(String username) {
        return getByUsernameOrThrow(username);
    }

    public Player updatePlayer(Integer id, Player player) {
        Player playerToUpdate = getOrThrow(id);
        playerToUpdate.setUsername(player.getUsername() == null ? playerToUpdate.getUsername() : player.getUsername());
        playerToUpdate.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        return playerRepository.save(playerToUpdate);
    }

    public Player chooseNewHand(Integer id, Hand hand) {
        Player playerToChoose = getOrThrow(id);
        playerToChoose.setHand(hand);
        return playerRepository.save(playerToChoose);
    }

    public Player replacePlayer(Integer id, Player player) {
        Player playerToReplace = getOrThrow(id);
        playerToReplace.setUsername(player.getUsername());
        playerToReplace.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        playerToReplace.setWins(player.getWins());
        playerToReplace.setLosses(player.getLosses());
        playerToReplace.setDraws(player.getDraws());
        return playerRepository.save(playerToReplace);
    }

    public Player deletePlayer(Integer id) {
        Player playerToDelete = getOrThrow(id);
        playerRepository.delete(playerToDelete);
        return playerToDelete;
    }

    public void addWinToPlayer(Player player) {
        player.addWin();
        playerRepository.save(player);
    }

    public void addLossToPlayer(Player player) {
        player.addLoss();
        playerRepository.save(player);
    }

    public void addDrawToPlayer(Player player) {
        player.addDraw();
        playerRepository.save(player);
    }

    private Player getOrThrow(Integer id) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Player with ID " + id));
    }

    private Player getByUsernameOrThrow(String username) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Player with username " + username));
    }

    private Hand stringToHand(String handString) {
        if (isValidHand(handString)) {
            return Hand.valueOf(handString.toUpperCase());
        } else {
            System.out.println(handString);
            System.out.println("Invalid hand option chosen. Please choose a valid hand");
            return null;
        }
    }

    private boolean isValidHand(String handString) {
        List<Hand> hands = Arrays.asList(Hand.values());
        List<String> handValues = hands.stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
        return handValues.contains(handString.toUpperCase());
    }
}

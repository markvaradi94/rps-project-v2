package ro.fasttrack.rpsproject.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrack.rpsproject.domain.Hand;
import ro.fasttrack.rpsproject.domain.Player;
import ro.fasttrack.rpsproject.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public List<Player> allPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("{id}")
    public Player getPlayerById(@PathVariable(required = false) Integer id) {
        return playerService.findPlayerById(id);
    }

//    @GetMapping("{username}")
//    public Player getPlayerByUsername(@PathVariable String username) {
//        return playerService.findPlayerByUsername(username);
//    }

    @PostMapping
    public Player addPlayer(@RequestBody Player newPlayer) {
        return playerService.addPlayer(newPlayer);
    }

    @PutMapping("{id}/update")
    public Player updatePlayer(@PathVariable Integer id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @PutMapping("{id}/hand")
    public Player playerChooseHand(@PathVariable Integer id, @RequestBody Hand hand) {
        return playerService.chooseNewHand(id, hand);
    }

    @PutMapping("{id}/replace")
    public Player replacePlayer(@PathVariable Integer id, @RequestBody Player player) {
        return playerService.replacePlayer(id, player);
    }

    @DeleteMapping("{id}")
    public Player deletePlayer(@PathVariable Integer id) {
        return playerService.deletePlayer(id);
    }
}

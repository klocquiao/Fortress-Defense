package ca.cmpt213.as3.controller;

import ca.cmpt213.as3.model.*;
import ca.cmpt213.as3.wrapper.ApiBoardWrapper;
import ca.cmpt213.as3.wrapper.ApiGameWrapper;
import ca.cmpt213.as3.wrapper.ApiLocationWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {
    boolean isCheat = false;
    List<ApiGameWrapper> gameWrappers = new ArrayList<>();
    List<Game> games = new ArrayList<>();

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public static class FileNotFound extends RuntimeException {
        public FileNotFound(String str) {
            super(str);
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class BadRequest extends RuntimeException {
        public BadRequest(String str) {
            super(str);
        }
    }

    @GetMapping("/about")
    @ResponseStatus(HttpStatus.OK)
    public String getAboutMessage() {
        return "Kyle Locquiao";
    }

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiGameWrapper> getGames() {
        return gameWrappers;
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        Game newGame = new Game();
        ApiGameWrapper newGameWrapper = ApiGameWrapper.createApiGameWrapper(gameWrappers.size(), newGame);
        gameWrappers.add(newGameWrapper);
        games.add(newGame);

        return newGameWrapper;
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiGameWrapper getGame(@PathVariable("id") long gameID) {
        if (gameID >= gameWrappers.size() || gameID < 0) {
            throw new FileNotFound("Game with ID " + gameID + " does not exist!");
        }

        return gameWrappers.get((int) gameID);
    }

    @GetMapping("/games/{id}/board")
    @ResponseStatus(HttpStatus.OK)
    public ApiBoardWrapper getGameBoard(@PathVariable("id") long gameID) {
        if(gameID >= gameWrappers.size() || gameID < 0) {
            throw new FileNotFound("Game with ID " + gameID + " does not exist!");
        }

        return ApiBoardWrapper.createApiBoardWrapper(games.get((int) gameID));
    }

    @PostMapping("/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeMove(@PathVariable("id") long gameID, @RequestBody ApiLocationWrapper locationWrapper) {
        if (gameID >= gameWrappers.size() || gameID < 0) {
            throw new FileNotFound("Game with ID " + gameID + " does not exist!");
        }

        int row = locationWrapper.row;
        int col = locationWrapper.col;
        Coordinate targetCoordinate = new Coordinate(row, col);
        if (!targetCoordinate.isInBound()) {
            throw new BadRequest("Invalid coordinates entered!");
        }

        Game game = games.get((int) gameID);
        game.userAttackAtCoordinate(targetCoordinate);

        gameWrappers.set((int) gameID, ApiGameWrapper.createApiGameWrapper((int) gameID, game));
    }

    @PostMapping("/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeCheatMode(@PathVariable("id") long gameID, @RequestBody String command) {
        if (gameID >= gameWrappers.size() || gameID < 0) {
            throw new FileNotFound("Game with ID " + gameID + " does not exist!");
        }

        if (!command.matches("SHOW_ALL")) {
            throw new BadRequest("Invalid cheat command entered!");
        }

        games.get((int) gameID).activateCheats();
    }
}

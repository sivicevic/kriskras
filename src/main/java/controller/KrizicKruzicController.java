package controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import domain.KrizicKruzic;
import domain.Strategija;
import domain.Potez;
import domain.Igrac;
import service.KrizicKruzicService;

@RestController
@RequestMapping("/api/games")
public class KrizicKruzicController {

    Logger logger = LoggerFactory.getLogger(KrizicKruzicController.class);

    @Autowired
    KrizicKruzicService service;

    public KrizicKruzicController() {
        logger.debug("created");
    }

    // TODO - take data in RequestBody of "Options"
    @RequestMapping(value = "", method = RequestMethod.POST)
    public KrizicKruzic newGame(
            @RequestParam(value = "level", defaultValue = "HARD") Strategija level,
            @RequestParam(value = "computerPlaysAs", defaultValue = "O") Igrac computerPlaysAs) {

        logger.debug("newGame: level=" + level + " player=" + computerPlaysAs);
        return service.newGame(level, computerPlaysAs);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<KrizicKruzic> readGames() {
        logger.debug("games");
        return service.getGames();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public KrizicKruzic readGame(@PathVariable String id) {
        logger.debug("games");
        return service.find(id).get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGame(@PathVariable String id) {
        logger.debug("delete " + id);
        service.delete(id);
    }

    @RequestMapping(value = "/{id}/turn ", method = RequestMethod.PUT)
    public KrizicKruzic turn(@PathVariable String id, @RequestBody Potez move) {

        validateGame(id);

        logger.debug("turn - " + move);

        Optional<KrizicKruzic> game = service.find(id);

        // TODO - check for open game, valid move

        return service.addMove(game.get(), move);
    }

    @RequestMapping(value = "/{id}/autoturn ", method = RequestMethod.PUT)
    public Potez autoTurn(@PathVariable String id) {

        validateGame(id);

        logger.debug("autoturn");

        Optional<KrizicKruzic> game = service.find(id);

        // TODO - check for open game

        Potez move = service.nextMove(game.get());

        logger.debug("autoturn returning " + move);

        service.addMove(game.get(), move);

        return move;
    }

    // TODO - overload with Game object
    private void validateGame(String id) {
        this.service.find(id).orElseThrow(() -> new GameNotFoundException(id));
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameNotFoundException(String id) {
        super("could not find game '" + id + "'.");
    }
}

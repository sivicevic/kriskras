package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import domain.KrizicKruzic;
import domain.Potez;
import domain.Igrac;
import domain.Pozicija;
import domain.Status;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class KrizicKruzicLogic {

    private final KrizicKruzic game;

    public KrizicKruzicLogic(KrizicKruzic game) {
        this.game = game;
    }

    public Potez nextMove() {
        return new Potez(nextPosition(), turn());
    }

    public KrizicKruzic addMove(Potez move) {

        if (isOpen(move.getPosition())) {
            game.addMove(move);
        }
        // else throw exception?

        if (isDraw()) {
            game.setStatus(Status.DRAW);
        } else if (isWinner(Igrac.X)) {
            game.setStatus(Status.WIN);
            game.setWinner(Igrac.X);
        } else if (isWinner(Igrac.O)) {
            game.setStatus(Status.WIN);
            game.setWinner(Igrac.O);
        }
        return game;
    }

    boolean isDraw() {
        return (game.getMoves().size() == 9 && !isWinner(Igrac.X) && !isWinner(Igrac.O));
    }

    boolean isWinner(Igrac player) {
        return isWinner(game.movesAsMap(), player);
    }

    boolean isInPosition(Igrac player, int row, int col) {
        return (player == game.playerAt(row, col));
    }

    public Igrac turn() {
        int count = game.getMoves().size();
        if ((count & 1) == 0) {
            return Igrac.X;
        }
        return Igrac.O;
    }

    boolean isOpen(Pozicija position) {
        return null == game.playerAt(position);
    }

    public List<Pozicija> getOpenPositions() {
        return openPositions(game.movesAsMap());
    }

    Igrac lastTurn() {
        return turn() == Igrac.X ? Igrac.O : Igrac.X;
    }

    Pozicija oppositeOpen(Pozicija position1, Pozicija position2) {
        Igrac player = lastTurn();
        if (isOpen(position1) && player == game.playerAt(position2)) {
            return position1;
        }
        return null;
    }

    Pozicija sandwichOpen(Pozicija position1, Pozicija position2) {
        Pozicija middle = new Pozicija(2, 2);
        if (isOpen(position1) && (Igrac.O == game.playerAt(middle))
                && (Igrac.X == game.playerAt(position2))) {

            return position1;
        }
        return null;
    }

    boolean multipleWinningPositions(Pozicija position) {
        Map<Pozicija, Igrac> map = game.movesAsMap();
        map.put(position, turn());
        return (winningPositions(turn(), map).size() > 1);
    }

    Pozicija or(Pozicija... positions) {
        return Stream.of(positions).filter(position -> position != null)
                .findFirst().orElse(null);
    }

    Pozicija nextPosition() {
        switch (game.getLevel()) {
        case HARD:
            return or(  win(), 
                        block(), 
                        fork(), 
                        blockFork(), 
                        firstMove(),
                        center(), 
                        oppositeCorner(), 
                        sandwich(), 
                        emptyCorner(),
                        emptySide(), 
                        firstOpen());
        case EASY:
            return or(  emptyCorner(), 
                        emptySide(), 
                        firstOpen());

        default:
            return firstOpen();
        }

    }

    Pozicija openPosition(int row, int col) {
        Pozicija position = new Pozicija(row, col);
        if (isOpen(position)) {
            return position;
        }
        
        return null;
    }

    Pozicija firstOpen() {
        return getOpenPositions().iterator().next();
    }

    Pozicija win() {
        return winningPositions(turn(), game.movesAsMap()).stream().findFirst()
                .orElse(null);
    }

    Pozicija block() {
        return winningPositions(lastTurn(), game.movesAsMap()).stream()
                .findFirst().orElse(null);
    }

    Pozicija fork() {
        Optional<Pozicija> opt = getOpenPositions().stream()
                .filter(this::multipleWinningPositions).findFirst();

        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    Pozicija blockFork() {
        if (game.getMoves().size() == 3) {
            if (isInPosition(Igrac.X, 1, 1) && isInPosition(Igrac.X, 3, 3)) {
                return new Pozicija(1, 2);
            } else if (isInPosition(Igrac.X, 1, 3)
                    && isInPosition(Igrac.X, 3, 1)) {
                return new Pozicija(1, 2);
            } else if (isInPosition(Igrac.X, 3, 2)
                    && isInPosition(Igrac.X, 2, 3)) {
                return new Pozicija(3, 3);
            }
        }
        return null;
    }

    Pozicija firstMove() {
        if (game.getMoves().isEmpty()) {
            return new Pozicija(1, 1);
        }
        return null;
    }

    Pozicija oppositeCorner() {
        return or(  oppositeOpen(new Pozicija(1, 1), new Pozicija(3, 3)),
                    oppositeOpen(new Pozicija(3, 3), new Pozicija(1, 1)),
                    oppositeOpen(new Pozicija(3, 1), new Pozicija(1, 3)),
                    oppositeOpen(new Pozicija(1, 3), new Pozicija(3, 1)));
    }

    Pozicija center() {
        return openPosition(2, 2);
    }

    Pozicija sandwich() {
        return or(  sandwichOpen(new Pozicija(3, 3), new Pozicija(1, 1)),
                    sandwichOpen(new Pozicija(1, 1), new Pozicija(3, 3)),
                    sandwichOpen(new Pozicija(3, 1), new Pozicija(1, 3)),
                    sandwichOpen(new Pozicija(1, 3), new Pozicija(3, 1)));
    }

    Pozicija emptyCorner() {
        return or(  openPosition(1, 1), 
                    openPosition(1, 3), 
                    openPosition(3, 1),
                    openPosition(3, 3));
    }

    Pozicija emptySide() {
        return or(  openPosition(1, 2), 
                    openPosition(2, 1), 
                    openPosition(2, 3),
                    openPosition(3, 2));

    }

    // static methods

    static List<Pozicija> getAllPositions() {
        List<Pozicija> positions = new ArrayList<Pozicija>();
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                positions.add(new Pozicija(row, col));
            }
        }
        return positions;
    }

    static List<List<Pozicija>> getWinningCombos() {
        List<List<Pozicija>> wins = new ArrayList<List<Pozicija>>();

        wins.add(asList(new Pozicija(1, 1), new Pozicija(1, 2), new Pozicija(1,3)));
        wins.add(asList(new Pozicija(2, 1), new Pozicija(2, 2), new Pozicija(2,3)));
        wins.add(asList(new Pozicija(3, 1), new Pozicija(3, 2), new Pozicija(3,3)));

        wins.add(asList(new Pozicija(1, 1), new Pozicija(2, 1), new Pozicija(3,1)));
        wins.add(asList(new Pozicija(1, 2), new Pozicija(2, 2), new Pozicija(3,2)));
        wins.add(asList(new Pozicija(1, 3), new Pozicija(2, 3), new Pozicija(3,3)));

        wins.add(asList(new Pozicija(1, 1), new Pozicija(2, 2), new Pozicija(3,3)));
        wins.add(asList(new Pozicija(3, 1), new Pozicija(2, 2), new Pozicija(1,3)));

        return wins;
    }

    static boolean matches(Map<Pozicija, Igrac> moves, Igrac player, List<Pozicija> winningCombo) {
        return winningCombo.stream().allMatch(
                position -> moves.get(position) == player);
    }

    static boolean isWinner(Map<Pozicija, Igrac> moves, Igrac player) {
        return getWinningCombos().stream().anyMatch(
                combo -> matches(moves, player, combo));
    }

    static boolean isWinnerWithMove(Map<Pozicija, Igrac> moves, Igrac player, Pozicija position) {
        Map<Pozicija, Igrac> alteredMoves = new HashMap<Pozicija, Igrac>(moves);
        alteredMoves.put(position, player);
        return getWinningCombos().stream().anyMatch(
                combo -> matches(alteredMoves, player, combo));
    }

    static boolean isOpen(Map<Pozicija, Igrac> moves, Pozicija position) {
        return moves.get(position) == null;
    }

    static List<Pozicija> openPositions(Map<Pozicija, Igrac> moves) {
        return getAllPositions().stream()
                .filter(position -> isOpen(moves, position)).collect(toList());
    }

    static List<Pozicija> winningPositions(Igrac player, Map<Pozicija, Igrac> moves) {
        return openPositions(moves).stream()
                .filter(position -> isWinnerWithMove(moves, player, position))
                .collect(toList());
    }
}

package pl.stanislaw;

import java.util.*;

public class BotPlayer<T> implements Player<T> {

    private final String name;
    private final T type;

    public BotPlayer(T type) {
        this.name = "Bot Player";
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public int[] move(Game<T> game) throws CloneNotSupportedException {
        return getBestMove(game);
    }

    private boolean terminal(Game<T> game) {
        return game.boardFull() || game.playerWin(game.getPlayer1()) || game.playerWin(game.getPlayer2());
    }

    private int value(Game<T> game) {
        if (game.playerWin(game.getPlayer1())) {
            return (game.getPlayer1().getName().equals(name)) ? 1 : -1;
        }
        if (game.playerWin(game.getPlayer2())) {
            return (game.getPlayer2().getName().equals(name)) ? 1 : -1;
        }
        return 0;
    }

    private ArrayList<ArrayList<Integer>> actions(Game<T> game) {
        ArrayList<ArrayList<Integer>> availableFields = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.fieldAvaiable(i, j)) {
                    availableFields.add(new ArrayList<>(List.of(i, j)));
                }
            }
        }
        return availableFields;
    }

    private Game<T> result(Game<T> game, int x, int y, Player<T> currentPlayer) throws CloneNotSupportedException {
        Game<T> newGame = game.clone();
        newGame.makeMove(new int[]{x, y}, currentPlayer);
        return newGame;
    }

    private int minMax(Game<T> game) throws CloneNotSupportedException {
        if (terminal(game)) {
            return value(game);
        }

        if (game.getCurrentPlayer().getName().equals(name)) {
            int maxValue = Integer.MIN_VALUE;
            for (ArrayList<Integer> move : actions(game)) {
                Game<T> newGame = result(game, move.get(0), move.get(1), game.getCurrentPlayer());
                changePlayer(newGame);
                maxValue = Math.max(maxValue, minMax(newGame));
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for (ArrayList<Integer> move : actions(game)) {
                Game<T> newGame = result(game, move.get(0), move.get(1), game.getCurrentPlayer());
                changePlayer(newGame);
                minValue = Math.min(minValue, minMax(newGame));
            }
            return minValue;
        }
    }

    public int[] getBestMove(Game<T> game) throws CloneNotSupportedException {
        int bestValue = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();

        for (ArrayList<Integer> move : actions(game)) {
            Game<T> newGame = result(game, move.get(0), move.get(1), game.getCurrentPlayer());
            changePlayer(newGame);

            int moveValue = minMax(newGame);

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMoves.clear();
                bestMoves.add(new int[]{move.get(0), move.get(1)});
            }
        }

        int[] ints = bestMoves.get(0);
        System.out.println(ints[0] + " " + ints[1]);
        return ints;
    }

    private void changePlayer(Game<T> game) {
        game.setCurrentPlayer(
                (game.getCurrentPlayer() == game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1()
        );
    }

    @Override
    public String toString() {
        return name;
    }
}
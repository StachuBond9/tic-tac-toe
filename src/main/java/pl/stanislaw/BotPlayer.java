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
        System.out.println(getBestMove(game)[0] + " " + getBestMove(game)[1]);
        System.out.println(game.getBoard());
        return getBestMove(game);
    }

    private boolean terminal(Game<T> game) {
        return game.boardFull() || game.playerWin(game.getPlayer1()) || game.playerWin(game.getPlayer2());
    }

    private int value(Game<T> game, Player<T> currentPlayer) {
        if (game.playerWin(currentPlayer)) {
            return 1;
        } else if (game.boardFull()) {
            return 0;
        } else {
            return -1;
        }
    }

    private ArrayList<ArrayList<Integer>> actions(Game<T> game){
       ArrayList<ArrayList<Integer>> avaiableFields =  new ArrayList<>();
        for(int i = 0; i< 3; i++){
            for(int j = 0; j < 3; j++){
                if(game.fieldAvaiable(i, j)){
                    avaiableFields.add(new ArrayList<>(List.of(i,j)));
                }
            }
        }
        return avaiableFields;

    }

    private Game<T> result(Game<T> game, int x, int y, Player<T> currentPlayer) throws CloneNotSupportedException {
        Game<T> newGame = game.clone();
        newGame.makeMove(new int[]{x, y}, currentPlayer);
        return newGame;
    }


    private int minMax(Game<T> game) throws CloneNotSupportedException {
        if(terminal(game)){
            return value(game, game.getCurrentPlayer());
        }

        if(game.getCurrentPlayer().getName().equals(name))
        {
            int maxValue = Integer.MIN_VALUE;

            ArrayList<ArrayList<Integer>> avaiableMoves = actions(game);
            for (ArrayList<Integer> avaiableMove : avaiableMoves) {
                Game<T> newGame = result(game.clone(), avaiableMove.get(0), avaiableMove.get(1), game.getCurrentPlayer());
                int value = minMax(newGame);

                maxValue = Math.max(maxValue, value);

            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;

            ArrayList<ArrayList<Integer>> avaiableMoves = actions(game);
            for (ArrayList<Integer> move : avaiableMoves) {
                Game<T> newGame = result(game.clone(), move.get(0), move.get(1), game.getCurrentPlayer());
                int value = minMax(newGame);

                minValue = Math.min(minValue, value);

            }
            return minValue;
        }

    }

    public int[] getBestMove(Game<T> game) throws CloneNotSupportedException {
        int bestValue = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();

        ArrayList<ArrayList<Integer>> availableMoves = actions(game);

        for (ArrayList<Integer> move : availableMoves) {

            Game<T> newGame = game.clone();
            newGame.makeMove(new int[]{move.get(0), move.get(1)}, game.getCurrentPlayer());

            int moveValue = minMax(newGame);

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMoves.clear();
                bestMoves.add(new int[]{move.get(0), move.get(1)});
            } else if (moveValue == bestValue) {
                bestMoves.add(new int[]{move.get(0), move.get(1)});
            }
        }
        return bestMoves.get(0);
    }


    @Override
    public String toString() {
        return name;
    }

}

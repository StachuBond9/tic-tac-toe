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

    private int value(Game<T> game, Player<T> currentPlayer) {
        if(currentPlayer.getName().equals(name)){
            if (game.playerWin(currentPlayer)) {
                //System.out.println("wynik 1");
                return 1;
            } else if (game.boardFull()) {
               // System.out.println("wynik 0");
                return 0;
            } else {
                //System.out.println("wynik -1");
                return -1;
            }
        }
        else if (game.playerWin(currentPlayer)) {
            //System.out.println("wynik -1k");
            return -1;
        } else if (game.boardFull()) {
            //System.out.println("wynik 0k");
            return 0;
        } else {
            //System.out.println("wynik 1k");
            return 1;
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
       // System.out.println(newGame.getBoard());
       // System.out.println(x + " " + y);
        return newGame;
    }


    private int minMax(Game<T> game) throws CloneNotSupportedException {
        changePlayer(game);
        if (terminal(game)) {
            //System.out.println("Gra zakończona. " + game.getCurrentPlayer().getName() + " " + (game.boardFull() ? "Remis" : "Wygrana"));
            return value(game, game.getCurrentPlayer());
        }

        if (game.getCurrentPlayer().getName().equals(name)) {
            int maxValue = Integer.MIN_VALUE;

            ArrayList<ArrayList<Integer>> availableMoves = actions(game);
            for (ArrayList<Integer> availableMove : availableMoves) {
                //System.out.println("Evaluating move for bot: (" + availableMove.get(0) + ", " + availableMove.get(1) + ")");
                Game<T> newGame = result(game.clone(), availableMove.get(0), availableMove.get(1), game.getCurrentPlayer());
                int moveValue = minMax(newGame);
                maxValue = Math.max(maxValue, moveValue);
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            ArrayList<ArrayList<Integer>> availableMoves = actions(game);
            for (ArrayList<Integer> move : availableMoves) {
                //System.out.println("Evaluating move for human: (" + move.get(0) + ", " + move.get(1) + ")");
                Game<T> newGame = result(game.clone(), move.get(0), move.get(1), game.getCurrentPlayer());
                int moveValue = minMax(newGame);
                minValue = Math.min(minValue, moveValue);
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
                System.out.println(move.get(0) + " " + move.get(1));
            }

        }
        Random random = new Random();
        int i = random.nextInt(bestMoves.size());
        return bestMoves.get(i);
    }

    private void changePlayer(Game<T> game) {
        game.setCurrentPlayer((game.getCurrentPlayer() == game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1());
    }

    @Override
    public String toString() {
        return name;
    }

}

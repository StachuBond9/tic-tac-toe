package pl.stanislaw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class GameTest {

    @Test
    void playerWin() {
        //given
        Player<Character> player1 = new HumanPlayer<>("Player1", 'O');
        Player<Character> player2 = new HumanPlayer<>("Player2", 'X');
        Board<Character> board = new Board<>(' ');
        Game<Character> game = new Game<>(player1, player2, board);

        //when
        game.makeMove(new int[]{2, 1}, player1);
        game.makeMove(new int[]{1, 1}, player1);
        game.makeMove(new int[]{0, 1}, player1);

        //then
        Assertions.assertTrue(game.playerWin(player1));
    }

    @Test
    void botOWork() throws CloneNotSupportedException {
        //given
        Player<Character> player1 = new BotPlayer<>('O');
        Player<Character> player2 = new HumanPlayer<>("Player2", 'X');
        Board<Character> board = new Board<>(' ');
        Game<Character> game = new Game<>(player1, player2, board);


        //when then
        Assertions.assertTrue(makeAllGame(game));
    }

    private boolean makeAllGame(Game<Character> game) throws CloneNotSupportedException {
        int bWin = 0;
        int draw = 0;
        int hWin = 0;

        List<List<int[]>> moves = TicTacToeMoves.generateAllMoveCombinations(5);

        Set<List<int[]>> invalidPrefixes = new HashSet<>();

        int i = 0;
        for (List<int[]> move : moves) {
            Game<Character> newGame = game.clone();
            boolean isValid = true;
            for (int j = 1; j <= move.size(); j++) {
                List<int[]> prefix = move.subList(0, j);
                if (invalidPrefixes.contains(prefix)) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) {
                continue;
            }

            for (int j = 0; j < move.size(); j++) {
                int[] ints = move.get(j);


                newGame.makeMove(newGame.getPlayer1().move(newGame), newGame.getPlayer1());
                System.out.println("Bot Move " + newGame.getBoard());

                if (newGame.playerWin(newGame.getPlayer1())) {
                    bWin++;
                    break;
                } else if (newGame.boardFull()) {
                    draw++;
                    break;
                }

                System.out.println(ints[0] + " " + ints[1]);

                if (newGame.fieldAvaiable(ints[0], ints[1])) {
                    newGame.makeMove(ints, newGame.getPlayer2());
                } else {
                    System.out.println("Przerwij - pole zajęte");

                    invalidPrefixes.add(new ArrayList<>(move.subList(0, j + 1)));
                    isValid = false;
                    break;
                }

                System.out.println("Player move " + newGame.getBoard());

                if (newGame.playerWin(newGame.getPlayer2())) {
                    hWin++;
                    break;
                } else if (newGame.boardFull()) {
                    draw++;
                    break;
                }
            }

            if (isValid) {
                i++;
            }

            System.out.println("Bot : " + bWin);
            System.out.println("Draw : " + draw);
            System.out.println("Human : " + hWin);
            System.out.println(i);
        }

        System.out.println("Bot : " + bWin);
        System.out.println("Draw : " + draw);
        System.out.println("Human : " + hWin);
        return bWin/ i == 1;
    }


}
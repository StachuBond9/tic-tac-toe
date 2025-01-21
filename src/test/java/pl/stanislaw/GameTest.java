package pl.stanislaw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger bWin = new AtomicInteger(0);
        AtomicInteger draw = new AtomicInteger(0);
        AtomicInteger hWin = new AtomicInteger(0);
        AtomicInteger i = new AtomicInteger(0);

        List<List<int[]>> moves = TicTacToeMoves.generateAllMoveCombinations(5);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Set<List<int[]>> invalidPrefixes = Collections.synchronizedSet(new HashSet<>());

        executor.execute(() -> {
            for (List<int[]> move : moves) {
                Game<Character> newGame;
                try {
                    newGame = game.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
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

                    try {
                        newGame.makeMove(newGame.getPlayer1().move(newGame), newGame.getPlayer1());
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Bot Move " + newGame.getBoard());

                    if (newGame.playerWin(newGame.getPlayer1())) {
                        bWin.incrementAndGet();
                        break;
                    } else if (newGame.boardFull()) {
                        draw.incrementAndGet();
                        break;
                    }

                    System.out.println(ints[0] + " " + ints[1]);

                    if (newGame.fieldAvaiable(ints[0], ints[1])) {
                        newGame.makeMove(ints, newGame.getPlayer2());
                    } else {
                        System.out.println("Przerwij - pole zajęte");

                        synchronized (invalidPrefixes) {
                            invalidPrefixes.add(new ArrayList<>(move.subList(0, j + 1)));
                        }
                        isValid = false;
                        break;
                    }

                    System.out.println("Player move " + newGame.getBoard());

                    if (newGame.playerWin(newGame.getPlayer2())) {
                        hWin.incrementAndGet();
                        break;
                    } else if (newGame.boardFull()) {
                        draw.incrementAndGet();
                        break;
                    }
                }

                if (isValid) {
                    i.incrementAndGet();
                }

                synchronized (System.out) {
                    System.out.println("Bot : " + bWin);
                    System.out.println("Draw : " + draw);
                    System.out.println("Human : " + hWin);
                    System.out.println(i);
                }
            }
        });
        executor.shutdown();


        System.out.println("Bot : " + bWin);
        System.out.println("Draw : " + draw);
        System.out.println("Human : " + hWin);
        return bWin.get() / (double) i.get() == 1;
    }


}
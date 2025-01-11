package pl.stanislaw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void botOGame() throws CloneNotSupportedException {
        BotPlayer<Character> player1 = new BotPlayer<>('O');
        HumanPlayer<Character> player2 = new HumanPlayer<>("Player", 'X');
        Board<Character> board = new Board<>(' ');
        Game<Character> game = new Game<>(player1, player2, board);
        Assertions.assertTrue(analyzeGame(game) > 0.9);

    }

    private float[] allMoveMaker(Game<Character> game) throws CloneNotSupportedException {
        float bWin = 0;
        float hWin = 0;
        float draw = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (game.fieldAvaiable(x, y)) {
                    Game<Character> newGame = game.clone();
                    newGame.makeMove(new int[]{x, y}, newGame.getPlayer1());
                    if (newGame.playerWin(newGame.getPlayer1())) {
                        bWin++;
                    } else if (newGame.playerWin(newGame.getPlayer2())) {
                        hWin++;
                    } else if (newGame.boardFull()) {
                        draw++;
                    } else {
                        float[] result = allMoveMaker(newGame);
                        bWin += result[0];
                        hWin += result[1];
                        draw += result[2];
                    }
                }
            }
        }

        return new float[]{bWin, hWin, draw};
    }

    public float analyzeGame(Game<Character> game) throws CloneNotSupportedException {
        float[] results = allMoveMaker(game);

        float totalGames = results[0] + results[1] + results[2];  // 9!
        System.out.println("Bot win (procent): " + (results[0] / totalGames) * 100 + "%");
        System.out.println("Human win (procent): " + (results[1] / totalGames) * 100 + "%");
        System.out.println("Draw (procent): " + (results[2] / totalGames) * 100 + "%");
        return ((results[0] / totalGames) + (results[2] / totalGames));
    }


}
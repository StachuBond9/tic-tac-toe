package pl.stanislaw;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Game<Character> game;

    public static void main(String[] args) {
        System.out.println("Tic tac toe");
        System.out.println("AI( Y ) or 2 Players(N)");
        Scanner scanner = new Scanner(System.in);
        char gameTyp = scanner.next().charAt(0);

        if (gameTyp == 'N') {
            game = new Game<>(new HumanPlayer<>("Player 1", 'O'), new HumanPlayer<>("Player 2", 'O'), new Board<>(' '));
        } else if (gameTyp == 'Y') {
            System.out.println("Player O ( 1 ) or Player X ( 2 )");
            Scanner scanner1 = new Scanner(System.in);
            char playerNumber = scanner1.next().charAt(0);
            if (playerNumber == '1') {
                game = new Game<>(new HumanPlayer<>("Player", 'O'), new BotPlayer<>('X'), new Board<>(' '));
            } else if (playerNumber == '2') {
                game = new Game<>(new BotPlayer<>('O'), new HumanPlayer<>("Player", 'X'), new Board<>(' '));
            } else {
                return;
            }
        } else {
            return;
        }

        while (!game.boardFull() || !game.playerWin(game.getPlayer1()) || !game.playerWin(game.getPlayer2())) {

            printBoard();

            if (game.getPlayer1().getName().equals("Bot Player")) {
                int[] xy;
                do {
                    xy = game.getPlayer1().move();
                } while (!game.fieldAvaiable(xy[0], xy[1]));

                game.makeMove(xy, game.getPlayer1());
            } else {
                game.makeMove(data(), game.getPlayer1());
            }

            if (game.playerWin(game.getPlayer1())) {
                printBoard();
                System.out.println(game.getPlayer1() + " WIN");
                return;
            }

            if (game.boardFull()) {
                System.out.println("Draw");
            }
            printBoard();


            if (game.getPlayer2().getName().equals("Bot Player")) {
                int[] xy;
                do {
                    xy = game.getPlayer2().move();
                } while (!game.fieldAvaiable(xy[0], xy[1]));

                game.makeMove(xy, game.getPlayer2());
            } else {
                game.makeMove(data(), game.getPlayer2());
            }

            if (game.playerWin(game.getPlayer2())) {
                printBoard();
                System.out.println(game.getPlayer2() + " WIN");
                return;
            }


            if (game.boardFull()) {
                System.out.println("Draw");
            }
        }
    }

    private static int[] data() {
        int x, y;
        do {
            Scanner scanner1 = new Scanner(System.in);
            x = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            y = scanner2.nextInt();
        } while (!game.fieldAvaiable(x, y));
        return new int[]{x, y};
    }


    private static void printBoard() {
        System.out.println("\n---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + game.getBoard().get(i).get(j) + "|");
            }
            System.out.println("\n---------");
        }
    }

}
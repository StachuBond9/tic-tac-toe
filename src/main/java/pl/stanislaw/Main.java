package pl.stanislaw;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Game<Character> game = new Game<>(new Player<Character>("Player1", 'O'), new Player<Character>("Player2", 'X'), new Board<Character>(' '));

    public static void main(String[] args) {
        System.out.println("Tic tac toe");
        while (!game.boardFull() || !game.playerWin(game.getPlayer1()) || !game.playerWin(game.getPlayer2())) {
            printBoard();
            game.makeMove(data(), game.getPlayer1());
            if (game.playerWin(game.getPlayer1())) {
                printBoard();
                System.out.println(game.getPlayer1() + " WIN");
                return;
            }
            printBoard();
            game.makeMove(data(), game.getPlayer2());
            if (game.playerWin(game.getPlayer2())) {
                printBoard();
                System.out.println(game.getPlayer2() + " WIN");
                return;
            }

        }
        if (game.boardFull()) {
            System.out.println("Draw");
        }
    }
    private static int[] data() {
        int x, y;
        do {
            Scanner scanner1 = new Scanner(System.in);
            x = scanner1.nextInt();
            Scanner scanner2 = new Scanner(System.in);
            y = scanner2.nextInt();
        } while (!game.fieldAvaiable(x, y)  );
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
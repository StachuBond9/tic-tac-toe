package pl.stanislaw;

import java.util.List;

public class Game<T> {

    private final Player<T> player1;
    private final Player<T> player2;

    public Player<T> getPlayer1() {
        return player1;
    }

    public Player<T> getPlayer2() {
        return player2;
    }

    private Board<T> board;

    public List<List<T>> getBoard() {
        return board.getBoard();
    }

    public Game(Player<T> player1, Player<T> player2, Board<T> board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public boolean makeMove(int[] data, Player<T> player) {

        if (!fieldAvaiable(data[0], data[1])) {
            return false;
        }
        board.move(data[0], data[1], player);
        return true;
    }

    public boolean playerWin(Player<T> player) {
        if(acrossWin(player)){
            return true;
        }
        if(hWin(player)){
            return true;
        }
        if(vWin(player)){
            return true;
        }
        return false;
    }


    public boolean boardFull() {
        for (List<T> row : board.getBoard()) {
            for (T field : row) {
                if (field == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean fieldAvaiable(int x, int y) {
        if(x >= 3 || x < 0 || y >= 3|| y<0){
            return false;
        }
        return board.getBoard().get(x).get(y) == board.getEmptySign();
    }

    private boolean hWin(Player<T> player) {
        for (int i = 0; i < 3; i++) {
            int fields = 0;
            for (int y = 0; y < 3; y++) {
                if (board.getBoard().get(i).get(y) == player.getType()) {
                    fields++;
                }
            }
            if (fields == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean vWin(Player<T> player) {
        for (int i = 0; i < 3; i++) {
            int fields = 0;
            for (int j = 0; j < 3; j++) {
                if (board.getBoard().get(j).get(i) == player.getType()) {
                    fields++;
                }
            }
            if (fields == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean acrossWin(Player<T> player) {
        int fields = 0;

        for (int i = 0; i < 3; i++) {
            if (board.getBoard().get(i).get(i) == player.getType()) {
                fields++;
            }
        }
        if (fields == 3) {
            return true;
        }

        fields = 0;

        for (int i = 0; i < 3; i++) {
            if (board.getBoard().get(i).get(2 - i) == player.getType()) {
                fields++;
            }
        }
        return fields == 3;
    }


}

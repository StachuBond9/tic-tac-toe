package pl.stanislaw;

import java.util.ArrayList;
import java.util.List;

public class Board<T> implements Cloneable {

    List<List<T>> board = new ArrayList<>(3);
    private final T emptySign;

    public Board(T sign) {
        emptySign = sign;
        start(sign);
    }

    public void move(int x, int y, Player<T> player) {
        if (player == null) {
            System.out.println("Player is null, cannot make move.");
            return;
        }
        board.get(x).set(y, player.getType());
    }

    private void start(T sign) {
        for (int i = 0; i < 3; i++) {
            List<T> row = new ArrayList<>(3);
            for (int j = 0; j < 3; j++) {
                row.add(sign);
            }
            board.add(row);
        }
    }

    public List<List<T>> getBoard() {
        return board;
    }

    public T getEmptySign() {
        return emptySign;
    }

    @Override
    public Board<T> clone() {
        try {
            Board<T> cloned = (Board<T>) super.clone();
            List<List<T>> clonedBoard = new ArrayList<>(3);
            for (List<T> row : board) {
                clonedBoard.add(new ArrayList<>(row));  // Kopiowanie wierszy
            }
            cloned.board = clonedBoard;
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}

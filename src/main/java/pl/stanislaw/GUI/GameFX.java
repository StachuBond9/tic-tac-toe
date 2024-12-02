package pl.stanislaw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.stanislaw.Board;
import pl.stanislaw.Game;
import pl.stanislaw.Player;

public class GameFX {
    private final Game<String> game = new Game<>(new Player<>("Player1", "O"), new Player<>("Player2", "X"), new Board<>(" "));
    private Player<String> currentPlayer = game.getPlayer1();
    private boolean endGame = false;
    @FXML
    private GridPane board;
    @FXML
    private Label label;

    public boolean isEndGame() {
        return endGame;
    }

    @FXML
    public void btn(ActionEvent actionEvent) throws InterruptedException {
        Button clicked = (Button) actionEvent.getSource();
        int[] data = {GridPane.getRowIndex(clicked), GridPane.getColumnIndex(clicked)};
        game.makeMove(data, currentPlayer);
        clicked.setText(currentPlayer.getType());
        clicked.setDisable(true);
        if (game.playerWin(currentPlayer)) {
            label.setText(currentPlayer.getName() + " WIN!!!");
            disableBoard();
            endGame = true;
        } else if (currentPlayer == game.getPlayer1()) {
            currentPlayer = game.getPlayer2();
        } else currentPlayer = game.getPlayer1();
        if(game.boardFull()){
            label.setText("DRAW");
            endGame = true;

        }
    }

    public void disableBoard() {
        for (int i =0; i < board.getChildren().size(); i++){
            board.getChildren().get(i).setDisable(true);
        }
    }


}

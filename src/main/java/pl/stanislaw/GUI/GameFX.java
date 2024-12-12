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
            setDisable();
            endGame = true;
        } else if (currentPlayer == game.getPlayer1()) {
            currentPlayer = game.getPlayer2();
            label.setText("MOVE : " + currentPlayer.getName());
        } else {
            currentPlayer = game.getPlayer1();
            label.setText("MOVE : " + currentPlayer.getName());

        }

        if (game.boardFull()) {
            label.setText("DRAW");
            board.setDisable(true);
            endGame = true;
        }
    }

    private void setDisable(){
        for (Node child : board.getChildren()) {
            child.setDisable(true);
        }
    }





}

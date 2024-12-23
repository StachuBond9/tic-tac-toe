package pl.stanislaw.GUI;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.stanislaw.Board;
import pl.stanislaw.Game;
import pl.stanislaw.Player;

import java.io.IOException;


public class GameFX {
    private final Game<String> game = new Game<>(new Player<>("Player1", "O"), new Player<>("Player2", "X"), new Board<>(" "));
    private Player<String> currentPlayer = game.getPlayer1();
    @FXML
    private GridPane board;
    @FXML
    private Label label;
    @FXML
    private FlowPane window;

    @FXML
    public void btn(ActionEvent actionEvent) throws InterruptedException, IOException {
        Button clicked = (Button) actionEvent.getSource();
        int[] data = {GridPane.getRowIndex(clicked), GridPane.getColumnIndex(clicked)};
        game.makeMove(data, currentPlayer);
        clicked.setText(currentPlayer.getType());
        clicked.setDisable(true);

        if (game.playerWin(currentPlayer)) {
            label.setText(currentPlayer.getName() + " WIN!!!");
            setDisable();
            newGame(actionEvent);
            return;
        }
        if (game.boardFull()) {
            board.setDisable(true);
            label.setText("DRAW");
            newGame(actionEvent);
            return;
        }


        if (currentPlayer == game.getPlayer1()) {
            currentPlayer = game.getPlayer2();
            label.setText("MOVE : " + currentPlayer.getName() + " " + currentPlayer.getType());
        } else if (currentPlayer == game.getPlayer2()) {
            currentPlayer = game.getPlayer1();
            label.setText("MOVE : " + currentPlayer.getName() + " " + currentPlayer.getType());

        }


    }

    private void setDisable() {
        for (Node child : board.getChildren()) {
            child.setDisable(true);
        }
    }

    private void newGame(ActionEvent actionEvent) throws InterruptedException, IOException {
        Button replay = new Button("Replay");
        replay.setPrefWidth(board.getPrefWidth() / 4);
        replay.setPrefHeight(board.getPrefHeight() / 4);
        StackPane pane = new StackPane(replay);
        pane.setPrefWidth(board.getPrefWidth());
        pane.setPrefHeight(board.getPrefHeight());
        pane.setAlignment(Pos.CENTER);
        window.getChildren().remove(board);
        replay.setAlignment(Pos.CENTER);
        window.getChildren().add(pane);

        replay.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
                Parent root = fxmlLoader.load(); // Ładowanie nowego widoku
                Stage primaryStage = (Stage) (replay.getScene().getWindow());
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show(); // Wyświetlenie nowego widoku
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

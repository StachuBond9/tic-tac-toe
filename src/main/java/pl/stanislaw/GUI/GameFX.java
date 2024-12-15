package pl.stanislaw.GUI;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import pl.stanislaw.Board;
import pl.stanislaw.Game;
import pl.stanislaw.Player;

import java.io.IOException;
import java.util.EventObject;

public class GameFX {
    private final Game<String> game = new Game<>(new Player<>("Player1", "O"), new Player<>("Player2", "X"), new Board<>(" "));
    private Player<String> currentPlayer = game.getPlayer1();
    @FXML
    private GridPane board;
    @FXML
    private Label label;

    @FXML
    public void btn(ActionEvent actionEvent) throws InterruptedException, IOException {
        Button clicked = (Button) actionEvent.getSource();
        int[] data = {GridPane.getRowIndex(clicked), GridPane.getColumnIndex(clicked)};
        game.makeMove(data, currentPlayer);
        clicked.setText(currentPlayer.getType());
        clicked.setDisable(true);

         if (currentPlayer == game.getPlayer1()) {
            currentPlayer = game.getPlayer2();
            label.setText("MOVE : " + currentPlayer.getName() + " " + currentPlayer.getType());
        } else if(currentPlayer == game.getPlayer2()) {
            currentPlayer = game.getPlayer1();
            label.setText("MOVE : " + currentPlayer.getName()+ " " + currentPlayer.getType());

        }

        if (game.playerWin(currentPlayer)) {
            setDisable();
            label.setText(currentPlayer.getName() + " WIN!!!");
            newGame(actionEvent);
        }
        else if (game.boardFull()) {
            board.setDisable(true);
            label.setText("DRAW");
            newGame(actionEvent);
        }

    }

    private void setDisable(){
        for (Node child : board.getChildren()) {
            child.setDisable(true);
        }
    }

    private void newGame(@NotNull ActionEvent actionEvent) throws InterruptedException, IOException {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished( actionEvent1 -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        pause.play();
    }
}

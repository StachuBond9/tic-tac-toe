package pl.stanislaw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.stanislaw.*;

import java.io.IOException;

public class GameFX {
    private Game<String> game;
    private Player<String> currentPlayer;

    @FXML
    private GridPane board;
    @FXML
    private Label label;
    @FXML
    private FlowPane window;

    private String playerType;

    public void setGame(String type) {
        playerType = type;
        if (type.equals("Player O")) {
            game = new Game<>(new HumanPlayer<>("Player", "O"), new BotPlayer<>("X"), new Board<>(" "));
        } else if (type.equals("Player X")) {
            game = new Game<>(new BotPlayer<>("O"), new HumanPlayer<>("Player", "X"), new Board<>(" "));
        } else {
            game = new Game<>(new HumanPlayer<>("Player1", "O"), new HumanPlayer<>("Player2", "X"), new Board<>(" "));
        }
        currentPlayer = game.getPlayer1();
    }

    public void initialize() throws IOException, InterruptedException {

        if (playerType.equals("Player X")) {
            makeBotMove();
        }

        label.setText("MOVE : " + currentPlayer.getName() + " " + currentPlayer.getType());
    }

    @FXML
    public void playerMove(ActionEvent actionEvent) {
        try {
            Button clicked = (Button) actionEvent.getSource();
            int[] data = {GridPane.getRowIndex(clicked), GridPane.getColumnIndex(clicked)};
            game.makeMove(data, currentPlayer);
            clicked.setText(currentPlayer.getType());
            clicked.setDisable(true);

            if (checkForWin() || checkForDraw()) {
                return;
            }


            changePlayer();

            if (currentPlayer instanceof BotPlayer) {
                makeBotMove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeBotMove() {
        try {
            int[] xy;
            do {
                xy = currentPlayer.move();
            } while (!game.fieldAvaiable(xy[0], xy[1]));

            game.makeMove(xy, currentPlayer);
            Node botMoveNode = getNodeByRowColumn(board, xy[0], xy[1]);
            if (botMoveNode instanceof Button) {
                ((Button) botMoveNode).setText(currentPlayer.getType());
                ((Button) botMoveNode).setDisable(true);
            }


            if (checkForWin() || checkForDraw()) {
                return;
            }

            changePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void changePlayer() {
        currentPlayer = (currentPlayer == game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
        label.setText("MOVE : " + currentPlayer.getName() + " " + currentPlayer.getType());
    }

    private boolean checkForWin() {
        if (game.playerWin(currentPlayer)) {
            label.setText(currentPlayer.getName() + " WIN!!!");
            setDisable();
            return true;
        }
        return false;
    }

    private boolean checkForDraw() {
        if (game.boardFull()) {
            board.setDisable(true);
            label.setText("DRAW");
            return true;
        }
        return false;
    }


    @FXML
    public void newGame(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
            Parent root = fxmlLoader.load();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node getNodeByRowColumn(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (rowIndex == null) rowIndex = 0;
            if (columnIndex == null) columnIndex = 0;

            if (rowIndex == row && columnIndex == column) {
                return node;
            }
        }
        return null;
    }

    private void setDisable() {
        board.getChildren().forEach(node -> node.setDisable(true));
    }
}

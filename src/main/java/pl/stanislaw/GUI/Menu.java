package pl.stanislaw.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {

    public Button start;
    public Label label;
    GameFX gameFX = new GameFX();

    public void startPressed(ActionEvent actionEvent) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(gameFX.isEndGame()){
            Thread.sleep(3000);
                    }
    }

}

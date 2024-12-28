package pl.stanislaw.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @FXML
    public Button start;
    @FXML
    public Label label;
    @FXML
    public MenuButton botMenu;

    String plyerTyp;


    private Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic tac toe");
        primaryStage.show();

    }

    public void startPressed(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));

        GameFX controller = new GameFX();

        controller.setGame(plyerTyp);

        loader.setController(controller);

        Parent root = loader.load();
        primaryStage =(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }


    public void gameTyp(ActionEvent actionEvent) {

        MenuItem item = (MenuItem) actionEvent.getSource();
        plyerTyp = item.getText();
        System.out.println(item.getText());
    }
}

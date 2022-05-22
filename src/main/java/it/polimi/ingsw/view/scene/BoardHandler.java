package it.polimi.ingsw.view.scene;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.text.html.ImageView;
import java.awt.*;

public class BoardHandler extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("playerBoard.fxml"));
        primaryStage.setTitle("Eriantys");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void main(String[] args) {
        launch(args);
    }


}

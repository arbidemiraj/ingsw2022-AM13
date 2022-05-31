package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.ClientController;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.view.gui.controllers.ConnectionSceneController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.List;

public class JavaFxMain extends Application {
    private Stage stage;
    private SceneController sceneController;

    @Override
    public void start(Stage stage) throws Exception {

        System.setProperty("prism.allowhidpi", "false");

        sceneController = new SceneController(this);
        this.stage = stage;
        GUI view = new GUI(sceneController);
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/connection_scene.fxml"));

        Parent root = fxmlLoader.load();
        ConnectionSceneController controller = fxmlLoader.getController();
        controller.addObserver(clientController);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Eriantys");
        stage.show();
    }

    public void changeScene(Scene scene){
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setContentText(message);
        alert.showAndWait();
    }

    public void startGame(Scene scene) {
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setScene(scene);
        stage.show();
    }

    public void showGenericAlert(String message) {
    }
}

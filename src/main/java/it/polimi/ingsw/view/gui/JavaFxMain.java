package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.controllers.ConnectionSceneController;
import it.polimi.ingsw.view.gui.controllers.GenericSceneController;
import it.polimi.ingsw.view.gui.controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class JavaFxMain extends Application {
    private Stage stage;
    private SceneController sceneController;

    @Override
    public void start(Stage stage) throws Exception {
        sceneController = new SceneController(this);
        this.stage = stage;
        Gui view = new Gui(sceneController);
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/connection_scene.fxml"));

        Parent root = fxmlLoader.load();
        ConnectionSceneController controller = fxmlLoader.getController();
        controller.addObserver(clientController);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.setTitle("Eriantys");
        stage.show();
    }

    public void changeScene(Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setContentText(message);
        alert.show();

    }
}

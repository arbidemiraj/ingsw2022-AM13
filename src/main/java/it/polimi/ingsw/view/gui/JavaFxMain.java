package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.ClientController;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.view.gui.controllers.ConnectionSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

/**
 * The main GUI application
 */
public class JavaFxMain extends Application {
    private Stage stage;
    private SceneController sceneController;

    public static void main(String[] args) {
        System.setProperty("prism.allowhidpi", "false");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Media media = new Media(String.valueOf(getClass().getResource("/Spitfires.mp3")));
        MediaPlayer music = new MediaPlayer(media);
        music.play();
        Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("Spitfires.mp3")).toExternalForm());
        MediaPlayer player = new MediaPlayer(pick);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(0.4);

        player.setOnEndOfMedia(() -> {
            player.seek(Duration.ZERO);
            player.play();
        });
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

    /**
     * Changes scene to the given one
     * @param scene the new scene
     */
    public void changeScene(Scene scene){
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Shows an error alert
     * @param message the error message
     */
    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the scene to the main game scene and starts the game
     * @param scene the main scene
     */
    public void startGame(Scene scene) {
        Media media = new Media(String.valueOf(getClass().getResource("/Spitfires.mp3")));
        MediaPlayer music = new MediaPlayer(media);
        Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("Spitfires.mp3")).toExternalForm());
        MediaPlayer player = new MediaPlayer(pick);

        music.stop();
        player.stop();

        stage.setResizable(true);
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setScene(scene);
        stage.show();
    }
}

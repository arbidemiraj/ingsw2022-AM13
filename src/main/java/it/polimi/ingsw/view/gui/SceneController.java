package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.controllers.BoardController;
import it.polimi.ingsw.view.gui.controllers.GenericSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.*;

/**
 * The controller of a generic scene
 */
public class SceneController extends ViewObservable {
    private Scene currentScene;
    private Parent root;
    private GenericSceneController currentController;
    private Map<String, String> pathMap = new HashMap<>();
    private Map<String, Scene> sceneMap = new HashMap<>();
    private Map<String, GenericSceneController> controllerMap = new HashMap<>();
    private JavaFxMain main;

    /**
     * Default constructor
     * @param main the main that runs the application
     */
    public SceneController(JavaFxMain main){
        this.main = main;
    }

    /**
     * Changes the scene by changing the root and loading the controller
     * @param observers the observers to add to the controller
     * @param fxml the fxml of the new scene
     */
    public void changeRoot(List<ViewObserver> observers, String fxml){
        GenericSceneController controller;

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((ViewObservable) controller).addAllObservers(observers);

            currentController = controller;
            Scene scene = new Scene(root);
            currentScene = scene;

        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
        }

        main.changeScene(currentScene);
    }

    /**
     * Changes scene by changing the root and
     * @param genericSceneController the controller of the scene already created
     * @param fxml the fxml of the scene
     */
    public void changeRoot(GenericSceneController genericSceneController, String fxml){
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            loader.setController(genericSceneController);
            currentController = genericSceneController;

            Parent root = loader.load();
            root.setId("pane");
            Scene scene = new Scene(root);
            currentScene = scene;


        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
        }

        main.changeScene(currentScene);
    }

    /**
     * Shows an error alert with the given message
     * @param message the message the alert shows
     */
    public void showAlert(String message){
        main.showAlert(message);
    }

    /**
     * Changes the scene to the main game scene and starts the game
     * @param controller the controller of the main game scene
     * @param fxml the new scene
     */
    public void startGame(BoardController controller, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            loader.setController(controller);
            currentController = controller;

            Parent root = loader.load();
            Scene scene = new Scene(root);
            currentScene = scene;

        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
        }

        main.startGame(currentScene);
    }
}

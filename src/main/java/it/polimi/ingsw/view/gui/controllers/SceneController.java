package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.JavaFxMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class SceneController extends ViewObservable {
    private Scene currentScene;
    private Parent root;
    private GenericSceneController currentController;
    private Map<String, String> pathMap = new HashMap<>();
    private Map<String, Scene> sceneMap = new HashMap<>();
    private Map<String, GenericSceneController> controllerMap = new HashMap<>();
    private static final String CONNECT = "connection_scene.fxml";
    public static final String LOGIN = "login_scene.fxml";
    private JavaFxMain main;


    public SceneController(JavaFxMain main){
        this.main = main;
    }

    public void setup(){
        List<String> fxmList = new ArrayList<>(Arrays.asList(CONNECT, LOGIN));

        try {
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(JavaFxMain.class.getResource("/fxml/" + path));
                sceneMap.put(path, new Scene(loader.load()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        currentScene = sceneMap.get(CONNECT);
    }

    public void changeRoot(List<ViewObserver> observers, String fxml){
        GenericSceneController controller;

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((ViewObservable) controller).addAllObservers(observers);

            currentController = (GenericSceneController) controller;
            Scene scene = new Scene(root);
            currentScene = scene;

        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
        }

        main.changeScene(currentScene);
    }


}

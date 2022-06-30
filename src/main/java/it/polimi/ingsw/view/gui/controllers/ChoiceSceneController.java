package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ListView;

/**
 * Handles the choice scene when the player has to choose if he wants to join or create a game
 */
public class ChoiceSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private Button newGameBtn;

    @FXML
    private Button joinGameBtn;

    @FXML
    private ListView games;

    /**
     * JavaFX initialize method of the controller
     */
    @FXML
    public void initialize() {
        newGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNewGame);
        joinGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGame);
    }

    /**
     * Method called when the new game button is clicked and notifies the choice to the observer
     * @param event
     */
    @FXML
    private void onNewGame(Event event) {
        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(1));
    }

    /**
     * Method called when the join game button is clicked and notifies the choice to the observer
     * @param event
     */
    @FXML
    private void onJoinGame(Event event) {
        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(2));
    }
}
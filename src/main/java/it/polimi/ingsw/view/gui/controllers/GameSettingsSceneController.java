package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Handles the game settings scene when a player creates a game
 */
public class GameSettingsSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private TextField numPlayers;

    @FXML
    private CheckBox expertMode;

    @FXML
    private Button backBtn;

    @FXML
    private Button joinGameBtn;

    /**
     * JavaFX initialize method for the controller
     */
    @FXML
    public void initialize() {
        joinGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGame);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackBtnClick);
    }

    @FXML
    private void onJoinGame(Event event) {
        int nPlayers = Integer.parseInt(numPlayers.getText());
        boolean expert = expertMode.isSelected();

        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateNewGame(nPlayers, expert))).start();
    }

    @FXML
    private void onBackBtnClick(Event event) {
        new Thread(() -> notifyObserver(viewObserver -> viewObserver.backToChoice())).start();
    }
}

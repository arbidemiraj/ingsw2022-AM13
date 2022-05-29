package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameSettingsSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private TextField numPlayers;

    @FXML
    private CheckBox expertMode;

    @FXML
    private Button joinGameBtn;

    @FXML
    public void initialize() {
        joinGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGame);
    }

    @FXML
    private void onJoinGame(Event event) {
        int nPlayers = Integer.parseInt(numPlayers.getText());
        boolean expert = expertMode.isSelected();

        notifyObserver(viewObserver -> viewObserver.onUpdateNewGame(nPlayers, expert));
    }
}

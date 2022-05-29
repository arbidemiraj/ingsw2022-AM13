package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ListView;


public class ChoiceSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private Button newGameBtn;

    @FXML
    private Button joinGameBtn;

    @FXML
    private ListView games;

    @FXML
    public void initialize() {
        newGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNewGame);
        joinGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGame);
    }

    @FXML
    private void onNewGame(Event event) {
        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(1));
    }


    @FXML
    private void onJoinGame(Event event) {
        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(2));
    }
}
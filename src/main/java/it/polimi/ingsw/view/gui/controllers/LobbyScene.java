package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class LobbyScene extends ViewObservable{

    private TextField serverAddress;

    private int serverPort;


    @FXML
    private Button loadGame;

    @FXML
    private Button newGame;

    @FXML
    public void initialize() {
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNewGame);
        loadGame.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onLoadGame);
    }

    private void onNewGame(Event event) {
    }


    private void onLoadGame(Event event) {


    }
}
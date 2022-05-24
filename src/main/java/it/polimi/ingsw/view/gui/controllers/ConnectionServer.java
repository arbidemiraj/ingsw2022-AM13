package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ConnectionServer extends ViewObservable{

    private TextField serverAddress;
    private TextField serverPort;
    
    @FXML
    private Button connectBtn;

    @FXML
    public void initialize() {
        connectBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConnectBtnClick);
    }

    private void onConnectBtnClick(Event event) {
        String address = serverAddress.getText();
        int port = Integer.parseInt(serverPort.getText());
    }


}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class ConnectionSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    
    @FXML
    private Button connectBtn;

    @FXML
    public void initialize() {
        connectBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConnectBtnClick);
    }

    @FXML
    private void onConnectBtnClick(Event event) {
        String address = serverAddress.getText();
        String port = serverPort.getText();

        ArrayList<String> serverInfo = new ArrayList<>();

        serverInfo.add(address);
        serverInfo.add(port);

        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateServerInfo(serverInfo))).start();
    }


}

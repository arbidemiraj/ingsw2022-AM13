package it.polimi.ingsw.view.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ConnectionServer extends ViewObservable{

    private TextField serverAddress;

    private int serverPort;

    @FXML
    private Button joinBtn;

    @FXML
    public void initialize() {
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);


    }


    private void onJoinBtnClick(Event event) {


        String Address = serverAddress.getText();
        int port = Integer.parseInt(serverPort.getText());

    }




}

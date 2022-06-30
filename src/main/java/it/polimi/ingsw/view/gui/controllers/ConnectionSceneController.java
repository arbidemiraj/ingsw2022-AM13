package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Handles the connection to the server scene
 */
public class ConnectionSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button connectBtn;

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    /**
     * JavaFx initialize method
     */
    @FXML
    public void initialize() {
        connectBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConnectBtnClick);
    }

    /**
     * The action when the button connect is clicked, sends the address and port
     * @param event
     */
    @FXML
    private void onConnectBtnClick(Event event) {
        boolean isValid = true;
        String address = serverAddress.getText();
        String port = serverPort.getText();

        ArrayList<String> serverInfo = new ArrayList<>();

        if(Integer.parseInt(port) < 1 && Integer.parseInt(port) > 65535 && !port.equals("")) isValid = false;
        if(!isValidIp(address) && !address.equals("") && !address.equals("localhost")) isValid = false;


        serverInfo.add(address);
        serverInfo.add(port);

        if(isValid == true) new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateServerInfo(serverInfo))).start();
        else {

        }
    }

    /**
     * Verifies if the given ip is valid
     * @param ip the given ip
     * @return true if it is valid or else false
     */
    public static boolean isValidIp(final String ip) {
        return PATTERN.matcher(ip).matches();
    }



}

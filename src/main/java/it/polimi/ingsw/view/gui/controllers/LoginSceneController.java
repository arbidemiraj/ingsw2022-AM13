package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginSceneController extends ViewObservable implements GenericSceneController{

        @FXML
        private TextField nicknameField;
        @FXML
        private Button joinBtn;

        @FXML
        public void initialize() {
            joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        }

        @FXML
        private void onJoinBtnClick(Event event) {
            String nickname = nicknameField.getText();

            notifyObserver(viewObserver -> viewObserver.onUpdateLoginMessage(nickname));
        }
}

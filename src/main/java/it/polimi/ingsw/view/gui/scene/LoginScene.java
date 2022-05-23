package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * This class implements the scene where users choose their nicknames.
 */
public class LoginScene extends ViewObservable {

    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinBtn;
    @FXML
    private Button backToMenuBtn;

    @FXML
    public void initialize() {
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        backToMenuBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToMenuBtnClick);
    }

    private void onJoinBtnClick(Event event) {


        String nickname = nicknameField.getText();

    }

    private void onBackToMenuBtnClick(Event event) {

    }
}

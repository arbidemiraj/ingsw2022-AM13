package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class MatchSettings extends ViewObservable{

    private TextField numPlayersField;
    private TextField nicknamesField;
    private TextField towerColorField;
    private TextField expertModeField;

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

        int numPlayers =  Integer.parseInt(numPlayersField.getText());
        String nickname = nicknamesField.getText();
        String towerColor = towerColorField.getText();
        int expertMode =  Integer.parseInt(expertModeField.getText());

    }

    private void onBackToMenuBtnClick(Event event) {

    }


}

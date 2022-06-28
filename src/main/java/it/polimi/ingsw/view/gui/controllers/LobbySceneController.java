package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LobbySceneController extends ViewObservable implements GenericSceneController {
    @FXML
    private Button joinGameBtn;

    @FXML
    private Button backBtn;

    private String games;

    @FXML
    private TextArea gamesLobby;

    @FXML
    private TextField gameId;

    @FXML
    public void initialize() {
        joinGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinGame);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackClick);
        gamesLobby.setText(games);
    }

    @FXML
    private void onJoinGame(Event event) {
        int id = Integer.parseInt(gameId.getText());
        new Thread(()->notifyObserver(viewObserver -> viewObserver.onUpdateJoinGame(id))).start();
    }

    @FXML
    private void onBackClick(Event event) {
        new Thread(()->notifyObserver(viewObserver -> viewObserver.backToChoice())).start();
    }

    public void addGames(String games){
        this.games = games;
    }
}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class TowerColorController extends ViewObservable implements GenericSceneController {
    @FXML
    private Button selectBtn;

    private List<String> col = new ArrayList<>();

    @FXML
    private ChoiceBox<String> towerColors;

    @FXML
    private Label info;

    @FXML
    public void initialize() {
        selectBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onSelectBtnClick);

        towerColors.getItems().addAll(col);
    }

    @FXML
    private void onSelectBtnClick(Event event) {
        String color = towerColors.getValue();

        info.setText("You chose " + color + " wait for other players to join...");
        selectBtn.setDisable(true);
        towerColors.hide();

        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateTowerColor(color))).start();
    }

    public void addColorOptions(List<String> availableColors){
       this.col = availableColors;
    }
}

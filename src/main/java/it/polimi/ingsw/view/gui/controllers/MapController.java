package it.polimi.ingsw.view.gui.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MapController {
    public ImageView assistantCard;

    @FXML
    public void initialize() {
        assistantCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onAssistantBtnClick);
    }

    @FXML
    private void onAssistantBtnClick(Event event) {

    }

}

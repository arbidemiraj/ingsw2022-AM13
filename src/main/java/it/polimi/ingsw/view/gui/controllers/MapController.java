package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;

public class MapController {
    @FXML
    private Button quitButton;
    @FXML
    private AnchorPane anchorMap;

    Stage stage;

    public void quit(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("QUIT");
        alert.setHeaderText("you're quitting!");
        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) anchorMap.getScene().getWindow();
            System.out.println("you've quit");
            stage.close();
        }
    }
    public ImageView assistantCard;

    @FXML
    public void initialize() {
        assistantCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onAssistantBtnClick);
    }

    @FXML
    private void onAssistantBtnClick(Event event) {

    }

}

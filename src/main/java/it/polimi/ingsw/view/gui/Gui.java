package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.SceneController;
import javafx.application.Platform;

import java.util.*;

public class Gui extends ViewObservable implements View {
    private SceneController sceneController;

    public Gui(SceneController sceneController){
        this.sceneController = sceneController;
    }

    @Override
    public void askCreateOrJoin() {
        Platform.runLater(() -> sceneController.changeRoot(observers, "lobby_scene.fxml"));
    }

    @Override
    public void askUsername() {
       Platform.runLater(() -> sceneController.changeRoot(observers, "login_scene.fxml"));
    }

    @Override
    public void askGameSettings() {
        Platform.runLater(() -> sceneController.changeRoot(observers, "gameSettings_scene.fxml"));
    }

    @Override
    public void askTowerColor(List<TowerColor> availableColors) {

    }

    @Override
    public void startTurn() {

    }

    @Override
    public void successMessage() {

    }

    @Override
    public void disconnectionMessage() {

    }

    @Override
    public void error(String error) {
        Platform.runLater(() -> sceneController.changeRoot(observers, "gameSettings_scene.fxml"));
    }

    @Override
    public void showLobby(String lobby) {

    }

    @Override
    public void winMessage(String winner) {

    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {

    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askStudentToMove() {

    }

    @Override
    public void askIslandToMove() {

    }

    @Override
    public void connectionLost() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void showGenericMessage(String message) {

    }

    @Override
    public void setBoard(ReducedBoard reducedBoard) {

    }

    @Override
    public void createModel(ReducedModel reducedModel) {

    }

    @Override
    public void askMotherNatureMove() {

    }

    @Override
    public void setTurnInfo(int steps) {

    }

    @Override
    public void activateCharacter(int id) {

    }
}

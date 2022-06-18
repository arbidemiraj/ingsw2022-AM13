package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.exceptions.EmptyCloudException;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.BoardController;
import it.polimi.ingsw.view.gui.controllers.LobbySceneController;
import it.polimi.ingsw.view.gui.controllers.TowerColorController;
import javafx.application.Platform;

import java.util.*;

public class GUI extends ViewObservable implements View {
    private final SceneController sceneController;
    private BoardController controller;
    private ReducedModel reducedModel;
    private String playerUsername;
    private String firstPlayer;

    public GUI(SceneController sceneController){
        this.sceneController = sceneController;
    }

    @Override
    public void askCreateOrJoin() {
        Platform.runLater(() -> sceneController.changeRoot(observers, "choice_scene.fxml"));
    }

    @Override
    public void askUsername() {
       Platform.runLater(() -> sceneController.changeRoot(observers, "login_scene.fxml"));
    }

    @Override
    public void askGameSettings() {
        Platform.runLater(() -> sceneController.changeRoot(observers, "game_settings_scene.fxml"));
    }

    @Override
    public void askTowerColor(List<TowerColor> availableColors) {
        TowerColorController towerColorController = new TowerColorController();
        towerColorController.addAllObservers(observers);

        List<String> colors = new ArrayList<>();

        for(TowerColor towerColor : availableColors){
            colors.add(towerColor.toString());
        }

        towerColorController.addColorOptions(colors);

        Platform.runLater(() -> sceneController.changeRoot(towerColorController, "tower_color_scene.fxml"));

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
        Platform.runLater(() -> sceneController.showAlert(error));
    }

    @Override
    public void showLobby(String lobby) {
        LobbySceneController lobbySceneController = new LobbySceneController();

        lobbySceneController.addAllObservers(observers);
        lobbySceneController.addGames(lobby);

        Platform.runLater(() -> sceneController.changeRoot(lobbySceneController, "lobby_scene.fxml"));
    }

    @Override
    public void winMessage(String winner) {

    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {

        reducedModel.setDeck(assistantCards);
        reducedModel.setTurnCards(cardsPlayed);

        Platform.runLater(() -> controller.askCard());
    }

    @Override
    public void askCloud() {
        Platform.runLater(() -> controller.askCloud());
    }

    @Override
    public void askStudentToMove() {
        Platform.runLater(() -> controller.askStudent());
    }

    @Override
    public void askIslandToMove() {

    }

    @Override
    public void connectionLost() {

    }

    @Override
    public void startGame(String firstPlayer, ReducedModel reducedModel) {
        controller = new BoardController();
        this.reducedModel = reducedModel;
        reducedModel.setWizardId(reducedModel.getUsername().indexOf(playerUsername) + 1);
        reducedModel.setUsername(playerUsername);
        reducedModel.setCurrentPlayer(firstPlayer);
        controller.addAllObservers(observers);
        controller.setReducedModel(reducedModel);

        Platform.runLater(() -> sceneController.startGame(controller, "board.fxml"));
    }

    @Override
    public void showGenericMessage(String message) {
        Platform.runLater(() -> sceneController.showGenericAlert(message));
    }

    @Override
    public void setBoard(ReducedBoard reducedBoard) {
        this.reducedModel.setReducedBoard(reducedBoard);
    }

    @Override
    public void createModel(ReducedModel reducedModel) {
        this.reducedModel = reducedModel;
    }

    @Override
    public void askMotherNatureMove() {
        Platform.runLater(() -> controller.askMotherNature());
    }

    @Override
    public void setTurnInfo(int steps) {
        reducedModel.setMaxSteps(steps);
    }

    @Override
    public void activateCharacter(int id) {

    }

    @Override
    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    @Override
    public void mergeIsland(int island1, int island2) {
        reducedModel.getReducedBoard().mergeIslands(island1, island2);

        Platform.runLater(() -> controller.mergeIslands(island1, island2));
    }

    @Override
    public void updateModel(HashMap<String, Integer> turnCardsPlayed) {
        Platform.runLater(() -> controller.setTurnCards(turnCardsPlayed));

    }

    @Override
    public void changeProfOwner(String professorOwner, Student color) {
        reducedModel.setProfOwner(professorOwner, color);

        Platform.runLater(() -> controller.setProf(professorOwner, color));
    }

    @Override
    public void conquerIsland(int island, String islandOwner) {
        Platform.runLater(() -> controller.conquerIsland(island, islandOwner));
    }

    @Override
    public void updateMotherNature(int steps) {
        reducedModel.getReducedBoard().moveMotherNature(steps);
    }

    @Override
    public void updateBoard() {
        Platform.runLater(() -> controller.updateIslands());
    }

    @Override
    public void updateIslands(ArrayList<ReducedIsland> islands) {
        Platform.runLater(() -> {
            reducedModel.getReducedBoard().setIslands(islands);
            controller.updateIslands();
        });
    }

    @Override
    public void updateClouds(int cloudId) {
        try {
            reducedModel.getReducedBoard().getClouds()[cloudId].getStudentsFromCloud();
        } catch (EmptyCloudException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> controller.updateCloud(cloudId));
    }

    @Override
    public void fillClouds(Cloud[] clouds) {
        reducedModel.getReducedBoard().setClouds(clouds);

        Platform.runLater(() -> controller.fillClouds());
    }

    @Override
    public void askStudentEffect(int effectId) {

        Platform.runLater(() -> controller.askStudentEffect(effectId));
    }

    @Override
    public void askIslandEffect(int effectId) {
        Platform.runLater(() -> controller.askIslandEffect(effectId));
    }

    @Override
    public void askSwitch() {
        Platform.runLater(() -> controller.askSwitch());
    }

    @Override
    public void updateCharacterStudents(ArrayList<Student> students, int id) {
        reducedModel.getCharacterById(id).setStudents(students);

        Platform.runLater(() -> controller.updateCharacterStudents());
    }

    @Override
    public void notifyCharacterActivation(int effectId, boolean activated) {
        Platform.runLater(() -> controller.characterIsActivated(effectId, activated));
    }

    @Override
    public void askEffect12Students(Student color) {
        Platform.runLater(() -> controller.askEffect12Students(color));
    }
}

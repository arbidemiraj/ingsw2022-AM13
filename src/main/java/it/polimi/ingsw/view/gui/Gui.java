package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.util.List;

public class Gui extends ViewObservable implements View {
    @Override
    public void askCreateOrJoin() {

    }

    @Override
    public void askUsername() {

    }

    @Override
    public void askGameSettings() {

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

    }

    @Override
    public void showLobby(String lobby) {

    }

    @Override
    public void winMessage() {

    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {

    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askStudentToMove(int numStudents) {

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
    public void createBoard(ReducedBoard reducedBoard) {

    }

    @Override
    public void createModel(ReducedModel reducedModel) {

    }

    @Override
    public void askMotherNatureMove() {

    }
}

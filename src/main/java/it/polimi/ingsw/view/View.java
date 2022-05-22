package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;

import java.util.List;

public interface View {

    void askCreateOrJoin();

    void askUsername();

    void askGameSettings();

    void askTowerColor(List<TowerColor> availableColors);

    void startTurn();

    void successMessage();

    void disconnectionMessage();

    void error(String error);

    void showLobby(String lobby);

    void winMessage();

    void askCardToPlay(List<AssistantCard> assistantCards,  List<AssistantCard> cardsPlayed);

    void askCloud();

    void askStudentToMove(int numStudents);

    void askIslandToMove();

    void connectionLost();

    void startGame();

    void showGenericMessage(String message);

    void createBoard(ReducedBoard reducedBoard);

    void createModel(ReducedModel reducedModel);

    void askMotherNatureMove();
}

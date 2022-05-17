package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.TowerColor;

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

    void askCloud(List<Cloud> clouds);

    void askStudentToMove(List<Movable> possibleMoves);

    void askIslandToMove();

    void connectionLost();

    void startGame();

    void showGenericMessage(String message);
}

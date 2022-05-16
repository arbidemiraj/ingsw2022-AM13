package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;

import java.util.List;

public interface View {

    void askCreateOrJoin();

    void askUsername();

    void askGameSettings();

    void askTowerColor();

    void successMessage();

    void disconnectionMessage();

    void error(String error);

    void showLobby(String lobby);

    void winMessage();

    void askCardToPlay(List<AssistantCard> assistantCards);

    void askCloud();

    void askStudentToMove();

    void askIslandToMove();
}

package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface that represents the view, implemented by the CLI and the GUI
 */
public interface View {

    /**
     * Asks the player if he wants to create a new game or join an existing one
     */
    void askCreateOrJoin();

    /**
     *
     */
    void askUsername();

    void askGameSettings();

    void askTowerColor(List<TowerColor> availableColors);

    void error(String error);

    void showLobby(String lobby);

    void winMessage(String winner);

    void askCardToPlay(List<AssistantCard> assistantCards,  List<AssistantCard> cardsPlayed);

    void askCloud();

    void askStudentToMove();

    void startGame(String firstPlayer, ReducedModel reducedModel);

    void showGenericMessage(String message);

    void setBoard(ReducedBoard reducedBoard);

    void createModel(ReducedModel reducedModel);

    void askMotherNatureMove();

    void setTurnInfo(int steps);

    void activateCharacter(int id);

    void setPlayerUsername(String username);

    void mergeIsland(int island1, int island2);

    void updateModel(HashMap<String, Integer> turnCardsPlayed);

    void changeProfOwner(String professorOwner, Student color);

    void conquerIsland(int island, String islandOwner);

    void updateMotherNature(int steps);

    void updateIslands(ArrayList<ReducedIsland> islands);

    void updateClouds(int cloudId);

    void fillClouds(Cloud[] clouds);

    void askStudentEffect(int effectId);

    void askIslandEffect(int effectId);

    void askSwitch();

    void updateCharacterStudents(ArrayList<Student> students, int id);

    void notifyCharacterActivation(int effectId, boolean activated, String owner);

    void askEffect12Students(Student color);

    void setMotherNature(int motherNature);

    void showDisconnection(String username);

    void backToChoice();
}

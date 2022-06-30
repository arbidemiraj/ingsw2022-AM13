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
     * Asks to insert a username
     */
    void askUsername();

    /**
     * Asks the game setting when creating a new game
     */
    void askGameSettings();

    /**
     * Asks a tower color
     * @param availableColors the colors the user can choose
     */
    void askTowerColor(List<TowerColor> availableColors);

    /**
     * Shows an error to the user
     * @param error
     */
    void error(String error);

    /**
     * Show the lobby with the existing games
     * @param lobby the lobby
     */
    void showLobby(String lobby);

    /**
     * Shows the win message notifying the end of the game
     * @param winner the winner of the game
     */
    void winMessage(String winner);

    /**
     * Asks the user to play an assistant card
     * @param assistantCards the cards the user can play
     * @param cardsPlayed the cards played in this turn
     */
    void askCardToPlay(List<AssistantCard> assistantCards,  List<AssistantCard> cardsPlayed);

    /**
     * Asks the user to choose a cloud to unfill
     */
    void askCloud();

    /**
     * Asks the user to move a student
     */
    void askStudentToMove();

    /**
     * Notifies the start of the game
     * @param firstPlayer the first player of this turn
     * @param reducedModel the lighter version of the model with the information the client needs
     */
    void startGame(String firstPlayer, ReducedModel reducedModel);

    /**
     * Show a generic message to the user
     */
    void showGenericMessage(String message);

    /**
     * Sets the lighter version of the board in the reduced model
     * @param reducedBoard the reduced version of the game board
     */
    void setBoard(ReducedBoard reducedBoard);

    /**
     * creates the reduced model in the view
     * @param reducedModel the lighter version of the game logic and containing all the information the user needs to see
     */
    void createModel(ReducedModel reducedModel);

    /**
     * Asks the player to move mother nature
     */
    void askMotherNatureMove();

    /**
     * Sets the maximum number of steps that mother nature can make
     * @param steps
     */
    void setTurnInfo(int steps);

    /**
     * Notifies the activation of a character
     * @param id the id of the activated character
     */
    void activateCharacter(int id);

    /**
     * Sets the username of the client in the view
     * @param username
     */
    void setPlayerUsername(String username);

    /**
     * Merges the 2 given island in the reduced model and shows to the user
     * @param island1 the first island to merge
     * @param island2 the second island to merge
     */
    void mergeIsland(int island1, int island2);

    /**
     * Updates the model, used in the GUI to set the cards played in a turn
     * @param turnCardsPlayed the assistant cards currently played
     */
    void updateModel(HashMap<String, Integer> turnCardsPlayed);

    /**
     * Notifies the change of a professor owner
     * @param professorOwner the new professor owner
     * @param color the color of the professor changed
     */
    void changeProfOwner(String professorOwner, Student color);

    /**
     * Notifies that an island has been conquered
     * @param island the island that has been conquered
     * @param islandOwner the new island owner
     */
    void conquerIsland(int island, String islandOwner);

    /**
     * Notifies that mother nature has been moved
     * @param steps the number of steps mother nature has made
     */
    void updateMotherNature(int steps);

    /**
     * Updates the islands in the reduced model after a player action
     * @param islands the list of the reduced islands
     */
    void updateIslands(ArrayList<ReducedIsland> islands);

    /**
     * Notifies that a cloud has been unfilled
     * @param cloudId the id of the unfilled cloud
     */
    void updateClouds(int cloudId);

    /**
     * Refills the clouds at the start of a new turn
     * @param clouds the clouds filled
     */
    void fillClouds(Cloud[] clouds);

    /**
     * Asks the user to select a student for the asked effect
     * @param effectId the id of the effect asked
     */
    void askStudentEffect(int effectId);

    /**
     * Asks the user to select an island for the asked effect
     * @param effectId the id of the effect asked
     */
    void askIslandEffect(int effectId);

    /**
     * Asks the user to selects the student that he wants to switch after activating a card
     */
    void askSwitch();

    /**
     * Updates the student on a character card after they've been changed
     * @param students the students on the card
     * @param id the id of the card
     */
    void updateCharacterStudents(ArrayList<Student> students, int id);

    /**
     * Notifies the user that a card has been activated or disactivated
     * @param effectId the id of the effect activated o disactivated
     * @param activated true if the card has been activated, false if the card is no more active
     * @param owner the username of the player that activated the card
     */
    void notifyCharacterActivation(int effectId, boolean activated, String owner);

    /**
     * Notifies the character has been activated and removes the given color students
     * @param color the color of the students to remove
     */
    void askEffect12Students(Student color);

    /**
     * Changes mother nature in the reduced model
     * @param motherNature
     */
    void setMotherNature(int motherNature);

    /**
     * Shows to the user that a disconnection has happened
     * @param username
     */
    void showDisconnection(String username);

    /**
     * Used to go back to the choice scene in the GUI
     */
    void backToChoice();

    /**
     * Notifies the end of the no entry tile effect on an island
     * @param islandId the island with the no entry tile on it
     */
    void noEntryTile(int islandId);
}

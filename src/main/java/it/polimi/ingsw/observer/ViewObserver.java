
package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer interface implemented by the view controller
 */
public interface ViewObserver {
    /**
     * Creates a connection with the given address and port
     * @param serverInfo contains the address and the port of the server
     */
    void onUpdateServerInfo(List<String> serverInfo);

    /**
     * Notifies to the server the choice (New Game or Join Game)
     * @param choice
     */
    void onUpdateCreateOrJoin(int choice);

    /**
     * Sends a message notifying that the player wants to activate a character
     * @param effectId the id of the character he wants to activate
     */
    void onUpdateCharacter(int effectId);

    /**
     * Notifies to the server the cloud the player has chosen
     * @param cloudId the id of the chosen cloud
     */
    void onUpdateCloud(int cloudId);

    /**
     * Sends a message containing the color that the player has chosen
     * @param chosenTowerColor
     */
    void onUpdateTowerColor(String chosenTowerColor);

    /**
     * Sends a message notifying the disconnection
     */
    void onUpdateDisconnect();

    /**
     * Sends a message containing the id of the game the player wants to join
     * @param gameId the id of the game the player wants to join
     */
    void onUpdateJoinGame(int gameId);

    /**
     * Sends a message containing the username asking for login
     * @param username the username the player wants
     */
    void onUpdateLoginMessage(String username);

    /**
     * Sends a message containing the number of steps that mother nature has made
     * @param steps the number of steps that mother nature has been moved
     */
    void onUpdateMotherNature(int steps);

    /**
     * Sends a message to notify a student has been moved
     * @param from the position where the student was
     * @param color the color of the student
     * @param to the position where the student has been moved
     * @param id the id of the island in case it has been moved to an island
     */
    void onUpdateStudent(String from, String color, String to, int id);

    /**
     * Sends a message containing the settings to create a new game
     * @param maxPlayers the number of players of the game
     * @param expertMode boolean saying if the game is or not in expert mode
     */
    void onUpdateNewGame(int maxPlayers, boolean expertMode);

    /**
     * Sends a message containing the id of the played assistant card
     * @param assistantCardId the id of the played assistant card
     */
    void onUpdateCard(int assistantCardId);

    /**
     * Sends the chosen island asked to activate a character effect
     * @param chosenIsland the index of the chosen island
     * @param effectId the id of the effect activated
     */
    void onUpdateIslandEffect(int chosenIsland, int effectId);

    /**
     * Sends the chosen student asked to activate a character effect
     * @param chosenStudent the chosen student
     * @param effectId the id of the effect activated
     */
    void onUpdateStudentEffect(String chosenStudent, int effectId);

    /**
     * Sends a message containing the students that have been switched
     * @param students list containing the students switched
     * @param effectId the id of the effect activated
     */
    void onUpdateSwitchStudents(ArrayList<Student> students, int effectId);

    /**
     * Goes back to the ask choice scene in the GUI
     */
    void backToChoice();
}

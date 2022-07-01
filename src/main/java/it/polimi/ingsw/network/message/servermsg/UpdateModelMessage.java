package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.UpdateType;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This message is sent from the server to the client to update the lighter version of the model in the client
 * There can be different type of update
 */
public class UpdateModelMessage extends Message {
    @Serial
    private static final long serialVersionUID = 5954866997923358263L;

    private int island;
    private String towerColor;
    private ArrayList<ReducedIsland> islands;
    private int island1;
    private int island2;
    private String professorOwner;
    private Student color;
    private HashMap<String, Integer> turnCardsPlayed;
    private int maxSteps;
    private UpdateType updateType;

    /**
     * Update for the turn cards played
     * @param turnCardsPlayed map that associate a user to his played card
     */
    public UpdateModelMessage(HashMap<String, Integer> turnCardsPlayed) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.TURN_CARDS;
        this.turnCardsPlayed = turnCardsPlayed;
    }

    /**
     * Update for the islands
     * @param islands the reduced island updated after a move
     */
    public UpdateModelMessage(ArrayList<ReducedIsland> islands) {
        super("Server", MessageType.UPDATE_MODEL);
        this.updateType = UpdateType.ISLANDS;
        this.islands = islands;
    }

    /**
     * Update for the turn info
     * @param maxSteps the max steps of this turn
     */
    public UpdateModelMessage(int maxSteps) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.MAX_STEPS;
        this.maxSteps = maxSteps;
    }

    /**
     * Update for a professor owner change
     * @param professorOwner the username of the owner
     * @param color the color of the professor
     */
    public UpdateModelMessage(String professorOwner, Student color) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.PROFESSOR;

        this.professorOwner = professorOwner;
        this.color = color;
    }

    /**
     * Updates the merge of 2 islands
     * @param island1 the index of the first island
     * @param island2 the index of the second island
     */
    public UpdateModelMessage(int island1, int island2) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.MERGE;

        this.island1 = island1;
        this.island2 = island2;

    }

    /**
     * Update for conquer of an island
     * @param island the index of the conquered island
     * @param towerColor the color of the owner who conquered
     */
    public UpdateModelMessage(int island, String towerColor) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.CONQUER;

        this.island = island;
        this.towerColor = towerColor;

    }

    public ArrayList<ReducedIsland> getIslands() {
        return islands;
    }

    public int getIsland() {
        return island;
    }

    public String getTowerColor() {
        return towerColor;
    }

    public int getIsland1() {
        return island1;
    }

    public int getIsland2() {
        return island2;
    }

    public String getProfessorOwner() {
        return professorOwner;
    }

    public Student getColor() {
        return color;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public HashMap<String, Integer> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    @Override
    public String toString() {
        return "UpdateModelMessage{" +
                "updateType=" + updateType +
                '}';
    }
}

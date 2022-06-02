package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.UpdateType;

import java.util.List;

public class UpdateModelMessage extends Message {
    private int island;
    private String towerColor;
    private int island1;
    private int island2;
    private String professorOwner;
    private Student color;
    private List<AssistantCard> turnCardsPlayed;
    private int maxSteps;
    private UpdateType updateType;

    public UpdateModelMessage(List<AssistantCard> turnCardsPlayed) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.TURN_CARDS;
        this.turnCardsPlayed = turnCardsPlayed;
    }

    public UpdateModelMessage(int maxSteps) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.MAX_STEPS;
        this.maxSteps = maxSteps;
    }

    public UpdateModelMessage(String professorOwner, Student color) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.PROFESSOR;

        this.professorOwner = professorOwner;
        this.color = color;
    }

    public UpdateModelMessage(int island1, int island2) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.MERGE;

        this.island1 = island1;
        this.island2 = island2;

    }

    public UpdateModelMessage(int island, String towerColor) {
        super("Server", MessageType.UPDATE_MODEL);

        updateType = UpdateType.CONQUER;

        this.island = island;
        this.towerColor = towerColor;

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

    public List<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    @Override
    public String toString() {
        return "UpdateModelMessage{" +
                "updateType=" + updateType +
                '}';
    }
}

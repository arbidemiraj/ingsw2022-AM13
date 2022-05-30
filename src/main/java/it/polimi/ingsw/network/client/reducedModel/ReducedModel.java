package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReducedModel implements Serializable {
    private List<String> username;
    private ReducedBoard reducedBoard;
    private TowerColor color;
    private ReducedCharacter[] reducedCharacters;
    private String currentPlayer;
    private boolean isExpertMode;
    private int turnMaxSteps;
    private boolean isActive;
    private List<AssistantCard> deck;
    private List<AssistantCard> turnCards;

    public ReducedModel(List<String> username, TowerColor color, ReducedCharacter[] reducedCharacters, boolean isExpertMode) {
        this.username = username;
        this.color = color;
        this.reducedCharacters = reducedCharacters;
        this.isExpertMode = isExpertMode;
    }

    public ReducedModel(List<String> username, TowerColor color){
        this.username = username;
        this.color = color;
        isExpertMode = false;
    }



    public boolean isExpertMode() {
        return isExpertMode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getUsername() {
        return username;
    }

    public TowerColor getColor() {
        return color;
    }

    public ReducedCharacter[] getReducedCharacters() {
        return reducedCharacters;
    }

    public ReducedBoard getReducedBoard() {
        return reducedBoard;
    }

    public void setReducedBoard(ReducedBoard reducedBoard) {
        this.reducedBoard = reducedBoard;
    }

    public void setMaxSteps(int steps){
        this.turnMaxSteps = steps;
    }

    public int getMaxSteps() {
        return turnMaxSteps;
    }

    public void activateCharacter(int id){
        Arrays.stream(reducedCharacters).filter(reducedCharacter -> reducedCharacter.getEffectId() == id).collect(Collectors.toList())
                .get(0).activate();


    }

    public void moveStudent(String from, String student, String to, int id) {
        if(from.equals("ENTRANCE")) reducedBoard.getPlayerBoard().removeEntranceStudent(student);

        if(to.equals("ISLAND")) reducedBoard.addStudentToIsland(id, Student.valueOf(student));

        if(to.equals("HALL")) reducedBoard.getPlayerBoard().addHallStudent(student);
    }

    public void setDeck(List<AssistantCard> assistantCards) {
        this.deck = assistantCards;
    }

    public List<AssistantCard> getDeck() {
        return deck;
    }

    public List<AssistantCard> getTurnCards() {
        return turnCards;
    }

    public void setTurnCards(List<AssistantCard> turnCards) {
        this.turnCards = turnCards;
    }


}

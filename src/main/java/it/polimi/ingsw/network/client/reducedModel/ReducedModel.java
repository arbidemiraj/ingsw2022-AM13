package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.io.Serializable;

public class ReducedModel implements Serializable {
    private String username;
    private ReducedBoard reducedBoard;
    private TowerColor color;
    private ReducedCharacter[] reducedCharacters;
    private String currentPlayer;
    private boolean isExpertMode;
    private boolean isActive;

    public ReducedModel(String username, TowerColor color, ReducedCharacter[] reducedCharacters, boolean isExpertMode) {
        this.username = username;
        this.color = color;
        this.reducedCharacters = reducedCharacters;
        this.isExpertMode = isExpertMode;
    }

    public ReducedModel(String username, TowerColor color){
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

    public String getUsername() {
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
}

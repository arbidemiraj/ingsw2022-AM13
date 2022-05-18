package it.polimi.ingsw.network.client.reducedModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReducedModel implements Serializable {
    private ArrayList<String> players;
    private Map<String, String> playerColor;
    private ReducedCharacter[] reducedCharacters;
    private String currentPlayer;

    public ReducedModel(ArrayList<String> players, ReducedCharacter[] reducedCharacters, String currentPlayer) {
        this.players = players;
        playerColor = new HashMap<>();
        this.reducedCharacters = reducedCharacters;
        this.currentPlayer = currentPlayer;
    }

    public void addColor(String username, String color){
        playerColor.put(color, username);
    }
}

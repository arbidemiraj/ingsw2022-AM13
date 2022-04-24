package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> nicknameQueue;
    private ArrayList<Effect> activeEffects;
    private ArrayList<AssistantCard> turnCardsPlayed;



    public Controller(Game model) {
        this.model = model;
        this.nicknameQueue = new ArrayList<>(model.getTable().getNicknames());
        turnCardsPlayed = new ArrayList<>();

        firstPlayer();
    }

    public void applyIslandEffect(int id, Island chosenIsland){
        Player player = model.getTable().getPlayerByNickname(currentPlayer);

        List<Character> activatedCharacter = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList());

        activatedCharacter.get(0).applyEffect(chosenIsland);
    }

    public void applyStudentEffect(int id, Student chosenStudent){
        Player player = model.getTable().getPlayerByNickname(currentPlayer);

        List<Character> activatedCharacter = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList());

        activatedCharacter.get(0).applyEffect(chosenStudent);

    }

    public void applyEffect(int id){
        Player player = model.getTable().getPlayerByNickname(currentPlayer);

        List<Character> activatedCharacter = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList());

        activatedCharacter.get(0).applyEffect();
    }

    public void moveStudent(Move from,int position, Move to){
        to.addStudent(from.removeStudent(position));
    }

    public void firstPlayer() {
        int choose = (int) (Math.random() * model.getNumPlayers());

        currentPlayer = model.getTable().getPlayers().get(choose).getNickname();
    }

    private void setCurrentPlayer(){
        ArrayList<Integer> values = new ArrayList<>();

        for(Player player : model.getTable().getPlayers()){
            values.add(player.getLastCard().getValue());
        }

        int maxValue = values
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow();

        int maxValuePos = values.indexOf(maxValue);

        currentPlayer = model.getTable().getPlayers().get(maxValuePos).getNickname();
    }

    public String currentPlayer() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return model.getTable().getPlayerByNickname(currentPlayer);
    }

    public void nextPlayer() {
        int currentPlayerIndex = nicknameQueue.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < model.getNumPlayers()) currentPlayerIndex++;
        else currentPlayerIndex = 0;

        currentPlayer = nicknameQueue.get(currentPlayerIndex);
    }

    public void playCard(AssistantCard cardPlayed) throws CardAlreadyPlayedException {
        Player player = model.getTable().getPlayerByNickname(currentPlayer);

        if(!turnCardsPlayed.contains(cardPlayed))
            player.playCard(cardPlayed);

        if(turnCardsPlayed != null) {
            if (turnCardsPlayed.contains(cardPlayed)) throw new CardAlreadyPlayedException();
            else {
                player.playCard(cardPlayed);
                turnCardsPlayed.add(cardPlayed);
            }
        }
    }

    public boolean endingConditionCheck() {
        for (Player player : model.getTable().getPlayers()) {
            if(player.getNumTowers() == 0) return true;

            if(player.getDeck().isEmpty()) return true;
        }

        if(model.getTable().getIslands().size() <= 3) return true;

        if(model.getTable().getBag().isEmpty()) return true;

        return false;
    }

    public Game getGame() {
        return model;
    }
}

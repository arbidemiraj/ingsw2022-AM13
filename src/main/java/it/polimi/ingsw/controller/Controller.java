package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Controller {

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> nicknameQueue;
    private ArrayList<Effect> activeEffects;
    private ArrayList<AssistantCard> turnCardsPlayed;



    public Controller(Game model) {
        this.model = model;
        this.nicknameQueue = new ArrayList<>(model.getBoard().getNicknames());
        turnCardsPlayed = new ArrayList<>();
    }

    public void activateIslandCharacter(int id, Island chosenIsland) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList())
                .get(0);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenIsland);
        }
    }

    public void activateStudentCharacter(int id, Student chosenStudent) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList())
                .get(0);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenStudent);
        }

    }

    public void activateCharacter(int id) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = Arrays.asList(model.getCharacters())
                .stream()
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList())
                .get(0);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect();
        }
    }

    public void moveStudent(Movable from, Student color, Movable to){
        to.addStudent(from.removeStudent(color));
    }

    public void firstPlayer() {
        int choose = (int) (Math.random() * (model.getNumPlayers()));

        currentPlayer = model.getBoard().getPlayers().get(choose).getNickname();
    }

    private void setCurrentPlayer(){
        ArrayList<Integer> values = new ArrayList<>();

        for(Player player : model.getBoard().getPlayers()){
            values.add(player.getLastCard().getValue());
        }

        int maxValue = values
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow();

        int maxValuePos = values.indexOf(maxValue);

        currentPlayer = model.getBoard().getPlayers().get(maxValuePos).getNickname();
    }

    public String currentPlayer() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return model.getBoard().getPlayerByNickname(currentPlayer);
    }

    public void nextPlayer() {
        int currentPlayerIndex = nicknameQueue.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < model.getNumPlayers()) currentPlayerIndex++;
        else currentPlayerIndex = 0;

        currentPlayer = nicknameQueue.get(currentPlayerIndex);
    }

    public void playCard(AssistantCard cardPlayed) throws CardAlreadyPlayedException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

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
        for (Player player : model.getBoard().getPlayers()) {
            if(player.getNumTowers() == 0) return true;

            if(player.getDeck().isEmpty()) return true;
        }

        if(model.getBoard().getIslands().size() <= 3) return true;

        if(model.getBoard().getBag().isEmpty()) return true;

        return false;
    }

    public Game getGame() {
        return model;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayerNickname(){
        return currentPlayer;
    }
}

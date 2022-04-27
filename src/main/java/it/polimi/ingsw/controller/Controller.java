package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.effects.Actionable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;

import java.util.ArrayList;

public class Controller {

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> nicknameQueue;
    private ArrayList<Actionable> activeEffects;
    private ArrayList<AssistantCard> turnCardsPlayed;



    public Controller(Game model) {
        this.model = model;
        this.nicknameQueue = new ArrayList<>(model.getBoard().getNicknames());
        turnCardsPlayed = new ArrayList<>();
    }

    public void activateIslandCharacter(int id, Island chosenIsland) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenIsland);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost());
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    public void activateStudentCharacter(int id, Student chosenStudent) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenStudent);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost());
            model.getActivatedCharacters().add(character.getEffectId());
        }

    }

    public void activateCharacter(int id) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect();
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost());
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    public void moveStudent(Movable from, Student color, Movable to){
        to.addStudent(from.removeStudent(color));
    }

    public void firstPlayer() {
        int choose = (int) (Math.random() * (model.getNumPlayers()));

        currentPlayer = model.getBoard().getPlayers().get(choose).getNickname();
    }

    public void setCurrentPlayer(){
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

    public void moveMotherNature(int steps) throws InvalidMotherNatureMovesException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        if(steps > player.getMotherNatureMoves()) throw new InvalidMotherNatureMovesException();
        else{
            model.getBoard().moveMotherNature(steps);
        }
    }

    public ArrayList<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }
}

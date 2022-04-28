package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;

import java.util.ArrayList;

/**
 * Controller class
 */
public class Controller {

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> nicknameQueue;
    private ArrayList<AssistantCard> turnCardsPlayed;

    /**
     * Default constructor
     * @param model
     */

    public Controller(Game model) {
        this.model = model;
        this.nicknameQueue = new ArrayList<>(model.getBoard().getNicknames());
        turnCardsPlayed = new ArrayList<>();
    }

    /**
     * method used to activate a character after receiving the chosenIsland
     *
     * @param id        the id of the character
     * @param chosenIsland      the island the player choose for applying the effect
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateIslandCharacter(int id, Island chosenIsland) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenIsland);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    /**
     * method used to activate a character after receiving the chosen Student
     *
     * @param id        the id of the character
     * @param chosenStudent      the student the player choose to move
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateStudentCharacter(int id, Student chosenStudent) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenStudent);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }

    }

    /**
     * method used to activate the character that don't need parameters
     *
     * @param id        the id of the character
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateCharacter(int id) throws NotEnoughCoinException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect();
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    /**
     * Method used to move a student
     * @param from      the movable object where the student is
     * @param color     the color of the student the player wants to move
     * @param to        the movable object where the player wants to move the student
     */
    public void moveStudent(Movable from, Student color, Movable to){
        to.addStudent(from.removeStudent(color));
    }

    /**
     * Randomly chooses the first player and sets it as current player
     */
    public void firstPlayer() {
        int choose = (int) (Math.random() * (model.getNumPlayers()));

        currentPlayer = model.getBoard().getPlayers().get(choose).getNickname();
    }

    /**
     * At the start of the action phase is called to set the first player
     * based on the assistant cards played
     */
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
        model.setCurrentPlayer(nicknameQueue.indexOf(currentPlayer));
    }


    public String currentPlayer() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return model.getBoard().getPlayerByNickname(currentPlayer);
    }

    /**
     * Used on each phase to advance to another player turn
     */
    public void nextPlayer() {
        int currentPlayerIndex = nicknameQueue.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < model.getNumPlayers()) currentPlayerIndex++;
        else currentPlayerIndex = 0;

        currentPlayer = nicknameQueue.get(currentPlayerIndex);
        model.setCurrentPlayer(nicknameQueue.indexOf(currentPlayer));
    }

    /**
     * Method called when a user plays a card
     * @param cardPlayed        the card the player decided to play
     * @throws CardAlreadyPlayedException       the player can't play assistant card already played in this round
     */
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

    /**
     * Method called at the end of each round to verify if the game has to end
     * @return
     */
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

    /**
     * Get the current player nickname
     * @return  the current player nickname
     */
    public String getCurrentPlayerNickname(){
        return currentPlayer;
    }

    /**
     * Method called when the player wants to move mother nature
     * @param steps        number of steps he wants to move mother nature
     * @throws InvalidMotherNatureMovesException
     */
    public void moveMotherNature(int steps) throws InvalidMotherNatureMovesException {
        Player player = model.getBoard().getPlayerByNickname(currentPlayer);

        if(steps > player.getMotherNatureMoves()) throw new InvalidMotherNatureMovesException();
        else{
            model.getBoard().moveMotherNature(steps);
        }
    }

    /**
     * Get the card played in this round
     * @return      the card played in this round
     */
    public ArrayList<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    // arrray that hold all the usernames of the players
    //needs to be implemented
    private ArrayList<String> Players=new ArrayList<>();

    //verifies that the username passed hasn't been already used
    private boolean IsUnique (String username){
        int i=0;
        boolean uguale=false;
        while (uguale==false || i<Players().length){
            if (Players[i].equals(username)==false)
                uguale=true;
            i++;
        }
        return uguale;
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.CardAlreadyPlayedException;
import it.polimi.ingsw.model.exceptions.EmptyCloudException;
import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import it.polimi.ingsw.network.message.clientmsg.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.GameHandler;
import it.polimi.ingsw.observer.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Controller class
 */
public class GameController implements Serializable, Observer {

    @Serial
    private static final long serialVersionUID = 743913880093540550L;

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> activePlayers;
    private ArrayList<AssistantCard> turnCardsPlayed;
    private TurnController turnController;
    private GameHandler gameHandler;
    private int phase;
    private int playerTurn;


    /**
     * Default constructor
     * @param model
     */

    public GameController(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
        this.activePlayers = new ArrayList<>(model.getUsernames());
        turnCardsPlayed = new ArrayList<>();
        phase = 0;
        playerTurn = 0;
    }

    public static void removePlayer(String username) {
    }

    /**
     * method used to activate a character after receiving the chosenIsland
     *
     * @param id        the id of the character
     * @param chosenIsland      the island the player choose for applying the effect
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateIslandCharacter(int id, Island chosenIsland) throws NotEnoughCoinException {
        Player player = getCurrentPlayer();

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
        Player player = getCurrentPlayer();

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
        Player player = getCurrentPlayer();

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
    public static void moveStudent(Movable from, Student color, Movable to){
        to.addStudent(from.removeStudent(color));
    }

    /**
     * Randomly chooses the first player and sets it as current player
     */
    public void firstPlayer() {
        int choose = (int) (Math.random() * (model.getNumPlayers()));

        currentPlayer = model.getPlayers().get(choose).getUsername();
    }

    /**
     * At the start of the action phase is called to set the first player
     * based on the assistant cards played
     */
    public void setCurrentPlayer(){
        ArrayList<Integer> values = new ArrayList<>();

        for(Player player : model.getPlayers()){
            values.add(player.getLastCard().getValue());
        }

        int maxValue = values
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow();

        int maxValuePos = values.indexOf(maxValue);

        currentPlayer = model.getPlayers().get(maxValuePos).getUsername();
        model.setCurrentPlayer(activePlayers.indexOf(currentPlayer));
    }


    public String currentPlayer() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return model.getPlayerByUsername(currentPlayer);
    }

    /**
     * Used on each phase to advance to another player turn
     */
    public void nextPlayer() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < model.getNumPlayers()) currentPlayerIndex++;
        else currentPlayerIndex = 0;

        currentPlayer = activePlayers.get(currentPlayerIndex);
        model.setCurrentPlayer(activePlayers.indexOf(currentPlayer));
    }

    /**
     * Method called when a user plays a card
     * @param cardPlayed        the card the player decided to play
     * @throws CardAlreadyPlayedException       the player can't play assistant card already played in this round
     */
    public void playCard(AssistantCard cardPlayed) throws CardAlreadyPlayedException {
        Player player = model.getPlayerByUsername(currentPlayer);

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
        for (Player player : model.getPlayers()) {
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
     * Get the current player username
     * @return  the current player username
     */
    public String getCurrentPlayerUsername(){
        return currentPlayer;
    }

    /**
     * Method called when the player wants to move mother nature
     * @param steps number of steps he wants to move mother nature
     * @throws InvalidMotherNatureMovesException
     */
    public void moveMotherNature(int steps) throws InvalidMotherNatureMovesException {
        Player player = model.getPlayerByUsername(currentPlayer);

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

    public void moveStudentsFromCloud(int cloudId) {
        ArrayList<Student> students = new ArrayList<>();

        Cloud[] clouds = model.getBoard().getClouds();
        try {
            students = clouds[cloudId].getStudentsFromCloud();
        } catch (EmptyCloudException e) {
            e.printStackTrace();
        }

        getCurrentPlayer().getPlayerBoard().fillEntrance(students);
    }


    public void messageReceived (Message message){
        switch (message.getMessageType()) {
            case CLOUD -> {
                ChooseCloudMessage chooseCloudMessage = (ChooseCloudMessage) message;
                moveStudentsFromCloud (chooseCloudMessage.getCloudId());

                gameHandler.sendMessage(new AskCard(getCurrentPlayer().getDeck(), turnCardsPlayed), currentPlayer);
            }

            case MOVE_MOTHERNATURE -> {
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) message;
                try {
                    moveMotherNature(moveMotherNatureMessage.getSteps());
                } catch (InvalidMotherNatureMovesException e) {
                    e.printStackTrace();
                }

                playerTurn++;
                if(playerTurn < model.getNumPlayers()){
                    gameHandler.sendMessage(new AskCloud(), currentPlayer);
                } else {
                    playerTurn = 0;
                    phase = 0;
                    newTurn();
                }
            }

            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                moveStudent(studentMessage.getFrom(),studentMessage.getColor(),studentMessage.getTo());

                gameHandler.sendMessage(new AskMotherNature(), currentPlayer);
            }

            case PLAY_CARD -> {
                PlayCardMessage msg = (PlayCardMessage) message;
                AssistantCard assistantCard = model.getPlayerByUsername(msg.getUsername()).getAssistantCardById(msg.getAssistantCard());
                try {
                    playCard(assistantCard);
                }catch (CardAlreadyPlayedException e){
                    e.printStackTrace();
                }

                playerTurn++;

                if(playerTurn < model.getNumPlayers()){
                    nextPlayer();
                }else{
                    playerTurn = 0;
                    phase++;
                    nextPlayer();
                    actionPhase();
                }
            }

            case ACTIVATE_CHARACTER -> {
                ActivateCharacterMessage activateCharacterMessage = (ActivateCharacterMessage) message;

                switch (activateCharacterMessage.getEffectId()){
                    case 1 -> {
                        gameHandler.sendMessage(new AskStudent(1), message.getUsername());
                    }
                }
            }

        }
    }

    public void addPlayer(String username){
        model.addPlayer(username);
    }

    @Override
    public void update(Message message) {

    }

    public void startGame() {
        gameHandler.sendMessageToAll(new BoardMessage(model.getBoard()));
        
        newTurn();
    }

    private void newTurn() {
        gameHandler.sendMessage(new AskCloud(), currentPlayer);
    }

    private void actionPhase(){
        gameHandler.sendMessage(new AskStudent(3), currentPlayer);
    }


}
package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.GenericType;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.GameHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnController {

    private final Game game;
    private final GameController gameController;
    private final GameHandler gameHandler;
    private List<String> usernameQueue;
    private String currentPlayer;
    private ArrayList<AssistantCard> turnCardsPlayed;
    private int numTurn = 0;

    public TurnController(GameController gameController, GameHandler gameHandler) {
        this.gameController = gameController;
        this.gameHandler = gameHandler;
        this.game = gameController.getGame();
        turnCardsPlayed = new ArrayList<>();
        usernameQueue = new ArrayList<>();
    }

    /**
     * Randomly chooses the first player and sets it as current player
     */
    public void firstPlayer() {
        int choose = (int) (Math.random() * (game.getNumPlayers()));

        currentPlayer = game.getPlayers().get(choose).getUsername();
    }

    public void newTurn() {
        fillUsernameQueue();

        gameController.checkBag();
        game.getBoard().fillClouds();

        numTurn++;

        if(game.isExpertMode()){
            for(int id : game.getActivatedCharacters()){
                gameHandler.sendMessageToAll(new CharacterActivated(id, false));
            }
            game.getActivatedCharacters().clear();
        }

        turnCardsPlayed.clear();

        gameHandler.sendMessageToAllExcept(new GenericMessage(currentPlayer + " is playing his turn! wait...\n", GenericType.GENERIC), currentPlayer);
        gameHandler.sendMessageToAll(new FillCloudsMessage(game.getBoard().getClouds()));
        gameHandler.sendMessage(new AskCard(getCurrentPlayer().getDeck(), turnCardsPlayed), usernameQueue.get(0));
    }

    public void actionPhase(){
        fillUsernameQueue();

        gameHandler.sendMessageToAll(new UpdateModelMessage(game.getPlayerByUsername(usernameQueue.get(0)).getLastCard().getMaxMotherNatureMoves()));
        gameHandler.sendMessage(new AskStudent(), currentPlayer);
    }

    public void fillUsernameQueue() {
        usernameQueue.clear();

        List<Player> playersSorted = game.getPlayers()
                .stream()
                .sorted(Comparator.comparing(player -> player.getLastCard().getValue()))
                .toList();

        for(Player player : playersSorted){
            usernameQueue.add(player.getUsername());
        }

        this.currentPlayer = usernameQueue.get(0);
    }

    /**
     * Used on each phase to advance to another player turn
     */
    public boolean nextPlayer() {
        int currentPlayerIndex = usernameQueue.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < usernameQueue.size()) currentPlayerIndex++;
        else return false;

        currentPlayer = usernameQueue.get(currentPlayerIndex);
        game.setCurrentPlayer(usernameQueue.indexOf(currentPlayer));

        if(gameHandler != null) gameHandler.sendMessageToAllExcept(new GenericMessage(currentPlayer + " is playing his turn! wait...\n", GenericType.GENERIC), currentPlayer);

        return true;
    }


    public void calcCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the current player username
     * @return  the current player username
     */
    public String getCurrentPlayerUsername(){
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return game.getPlayerByUsername(currentPlayer);
    }

    /**
     * Get the card played in this round
     * @return      the card played in this round
     */
    public List<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    public void firstUsernameQueue() {

        usernameQueue.add(currentPlayer);

        for(Player player : game.getPlayers()){
            if(!player.getUsername().equals(currentPlayer)) usernameQueue.add(player.getUsername());
        }
    }
}

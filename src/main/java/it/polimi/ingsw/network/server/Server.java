package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.exceptions.DuplicateUsernameException;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.JoinGameMessage;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.AskTowerColor;
import it.polimi.ingsw.network.message.servermsg.LobbyMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Main server class that starts a socket server.
 * It can handle different types of connections.
 */
public class Server {

    private ArrayList<SocketClient> playersInLobby;

    private int nextGameId;

    private LobbyHandler lobbyHandler;

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server() {
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
        this.playersInLobby = new ArrayList<>();
        this.lobbyHandler = new LobbyHandler();
    }

    /**
     * Adds a client to be managed by the server instance
     *this creates a new player profile
     */
    public void addClient(String username, ClientHandler clientHandler) throws DuplicateUsernameException {
        if(!isUnique(username)) throw new DuplicateUsernameException();
        else {
            clientHandlerMap.put(username, clientHandler);
            lobbyHandler.login(username);
        }
    }


    /**
     *verifies if the player username is unique
     * @param username
     * @return boolean      true if it is unique, false if it isn't
     */
    private boolean isUnique (String username) {
        if(lobbyHandler.getUsernameQueue() != null && lobbyHandler.getUsernameQueue().contains(username)) return false;
        else return true;
    }

    /**
     * Allows to delete a player given his username
     */
    public void removeClient(String username, boolean notifyEnabled) {
        clientHandlerMap.remove(username);
        LOGGER.info("Removed " + username + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the GameController.
     */
    public void messageReceived(Message message) {
        switch (message.getMessageType()){
            case NEW_GAME -> {
                clientHandlerMap.get(message.getUsername()).setGameId(nextGameId);
                createNewGame((NewGameMessage) message);
                lobbyHandler.getGames().get(nextGameId - 1).addPlayer(message.getUsername());
                lobbyHandler.joinGame(message.getUsername(), nextGameId - 1);
                clientHandlerMap.get(message.getUsername()).sendMessage(new GenericMessage("\n Waiting players to join ..."));
            }

            case JOIN_GAME -> {
                JoinGameMessage joinGameMessage = (JoinGameMessage) message;
                clientHandlerMap.get(message.getUsername()).setGameId(joinGameMessage.getGameId());
                lobbyHandler.getGames().get(joinGameMessage.getGameId()).addPlayer(message.getUsername());
                lobbyHandler.joinGame(message.getUsername(), joinGameMessage.getGameId());
            }

            default -> {
                lobbyHandler.receivedMessage(message, lobbyHandler.getGameIdFromUsername(message.getUsername()));
            }
        }
    }

    /**
     * Handles the disconnection of a client.
     */
    public void disconnect(ClientHandler clientHandler) {
    }

    /**
     * Returns the corresponding username of a ClientHandler
     */
    private String getUsernameFromClientHandler(ClientHandler clientHandler) {
        return clientHandlerMap.entrySet()
                .stream()
                .filter(entry -> clientHandler.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    public void createNewGame(NewGameMessage newGameMessage){
        GameHandler gameHandler = new GameHandler(this, newGameMessage, nextGameId);
        lobbyHandler.newGame(gameHandler);
        nextGameId++;
    }

    public Message getLobby(){
        return new LobbyMessage(lobbyHandler.printLobby());
    }
}
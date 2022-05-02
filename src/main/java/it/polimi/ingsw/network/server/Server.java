package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.*;

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

    private Controller controller;

    private ArrayList<SocketClient> playersInLobby;

    private int nextGameId;

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server() {
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
        this.playersInLobby = new ArrayList<>();
    }

    /**
     * Adds a client to be managed by the server instance
     *this creates a new player profile
     */
    public void addClient(String username, ClientHandler clientHandler) {
        clientHandlerMap.put(username, clientHandler);
    }

    /**
     * Allows to delete a player given his username
     */
    public void removePlayer(String username, boolean notifyEnabled) {
        clientHandlerMap.remove(username);
        Controller.removePlayer(username);
        LOGGER.info(() -> "Removed " + username + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the GameController.
     */
    public void messageReceived(Message message) {

        switch (message.getMessageType()){
            case NEW_GAME -> createNewGame((NewGameMessage) message);
            case JOIN_GAME -> {
                LobbyMessage lobbyMessage = new LobbyMessage();
                clientHandlerMap.get(message.getUsername()).sendMessage(lobbyMessage);

            }
            default -> controller.messageReceived(message);
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

    public void createNewGame(NewGameMessage newGameMessage){
        GameHandler gameHandler = new GameHandler(this, newGameMessage, nextGameId);
        nextGameId++;
    }
}
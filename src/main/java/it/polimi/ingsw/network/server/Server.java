package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.NewGameMessage;

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

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server() {
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    /**
     * Adds a client to be managed by the server instance
     *this creates a new player profile
     */
    public void addPlayer(String username, ClientHandler clientHandler) {

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
        controller.messageReceived(message);
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
        Game game = new Game(newGameMessage.getMaxPlayers(), newGameMessage.isExpertMode());
        controller = new Controller(game);
    }
}
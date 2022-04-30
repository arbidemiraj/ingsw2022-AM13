package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.message.Message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Main server class that starts a socket server.
 * It can handle different types of connections.
 */
public class Server {

    private final Controller controller;

    private final Map<String, ClientHandler> clientHandlerMap;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(Controller controller) {
        this.controller = controller;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    /**
     * Adds a client to be managed by the server instance
     *this creates a new player profile
     */
    public void addPlayer(String nickname, ClientHandler clientHandler) {

    }

    /**
     * Allows to delete a player given his username
     */
    public void removePlayer(String username, boolean notifyEnabled) {
        clientHandlerMap.remove(username);
        //Controller.removePlayer(username, notifyEnabled);
        LOGGER.info(() -> "Removed " + username + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the GameController.
     */
    public void messageReceived(Message message) {
        Controller.messageReceived(message);
    }

    /**
     * Handles the disconnection of a client.
     */
    public void Disconnect(ClientHandler clientHandler) {
        synchronized (lock) {
            String username = getUsernameFromClientHandler(clientHandler);

            /*if (username != null) {

                boolean gameStarted = controller.isGameStarted();

                if(controller.getTurn() != null &&
                        !controller.getTurn().getUsernameQueue().contains(username)) {
                    return;
                }

                if (gameStarted) {
                    controller.DisconnectionMessage(username, " disconnected from the server. GAME ENDED.");
                    controller.endGame();
                }
            }*/
        }
    }


    /**
     * Returns the corresponding nickname of a ClientHandler
     */
    private String getUsernameFromClientHandler(ClientHandler clientHandler) {
        return clientHandlerMap.entrySet()
                .stream()
                .filter(entry -> clientHandler.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
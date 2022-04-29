package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

/**
 * Interface to handle clients. Every type of connection must implement this interface.
 */
public interface ClientHandler {

    boolean isConnected();

    /**
     * Disconnects from the client.
     */
    void disconnect();


    void sendMessage(Message message);
}
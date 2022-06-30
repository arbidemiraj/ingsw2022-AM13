package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

/**
 * Interface implemented by the class that need to observe
 */
public interface Observer {
    /**
     * Handles a received message
     * @param message the received message
     */
    void update(Message message);
}

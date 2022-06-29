package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

/**
 * Interface implemented by the class that need to observe
 */
public interface Observer {
    void update(Message message);
}

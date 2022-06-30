package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.Observable;


import java.util.logging.Logger;

    /**
     * Abstract class to communicate with the server where every type of connection must implement this interface.
     */
    public abstract class Client extends Observable {

        public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

        /** Send message **/

        public abstract void sendMessage(Message message);

        /**
         * Asynchronously reads a message from the server and notifies the ClientController.
         */
        public abstract void readMessage();

        /**
         * Disconnects from the server.
         */
        public abstract void disconnect();


        public abstract void enablePing(boolean enabled);
    }


package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class Disconnection extends Message {

    public Disconnection() {
        super("server",MessageType.DISCONNECTED);
    }
    public String toString(){
        return "connection has dropped " +
                getUsername() + '}';
    }
}

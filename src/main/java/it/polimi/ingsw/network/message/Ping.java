package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
* Ping message to very and keep alive the connection
*/
public class Ping extends Message {
    @Serial
    private static final long serialVersionUID = -3234502390970792179L;

    public Ping (){
        super("Ping", MessageType.PING);
    }
}

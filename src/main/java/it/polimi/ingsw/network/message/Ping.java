package it.polimi.ingsw.network.message;

/*
* ping message to very and keep alive the connection
*/
public class Ping extends Message {
    private static final long serialVersionUID = -3234502390970792179L;

    public Ping (){
        super(null, MessageType.PING);
    }
}

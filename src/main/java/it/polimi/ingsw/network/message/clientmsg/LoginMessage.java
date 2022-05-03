package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * Message used by the client to request a login to the server.
 */
public class LoginMessage extends Message {


    @Serial
    private static final long serialVersionUID = -3234502390970792179L;

    public LoginMessage(String username) {
        super(username, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username=" + getUsername() +
                '}';
    }
}
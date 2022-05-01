package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * Message used by the client to request a login to the server.
 */
public class Login extends Message {


    @Serial
    private static final long serialVersionUID = -3234502390970792179L;

    public Login(String nickname) {
        super(nickname, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "nickname=" + getNickname() +
                '}';
    }
}
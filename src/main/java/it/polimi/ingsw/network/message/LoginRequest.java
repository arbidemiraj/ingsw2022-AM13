package it.polimi.ingsw.network.message;

import java.io.Serial;

/**
 * Message used by the client to request a login to the server.
 */
public class LoginRequest extends Message {


    @Serial
    private static final long serialVersionUID = -6112309482264268808L;

    public LoginRequest(String nickname) {
        super(nickname, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "nickname=" + getNickname() +
                '}';
    }
}
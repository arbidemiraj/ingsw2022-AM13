package it.polimi.ingsw.network.message;

import java.io.Serial;
import java.util.List;

/**
 * Message containing the information of the lobby
 */
public class LobbyMessage extends Message {

    @Serial
    private static final long serialVersionUID = 8149425735430360323L;


        public LobbyMessage() {
            super("server", MessageType.LOBBY);
        }

        @Override
        public String toString() {
            return "LobbyMessage{" +
                    "username=" + getUsername() +
                    '}';
        }

    }

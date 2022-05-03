package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * Message containing the information of the lobby
 */
public class LobbyMessage extends Message {

    @Serial
    private static final long serialVersionUID = 8149425735430360323L;
        private String lobby;

        public LobbyMessage(String lobby) {
            super("server", MessageType.LOBBY);
            this.lobby = lobby;
        }

        @Override
        public String toString() {
            return lobby;
        }

    }

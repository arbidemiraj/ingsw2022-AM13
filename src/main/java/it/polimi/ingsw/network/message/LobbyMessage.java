package it.polimi.ingsw.network.message;

import java.io.Serial;
import java.util.List;

/**
 * Message containing the information of the lobby
 */
public class LobbyMessage extends Message {

    @Serial
    private static final long serialVersionUID = 8149425735430360323L;
    private final List<String> usernameList;
        private final int maxPlayers;

        public LobbyMessage(List<String> usernameList, int maxPlayers) {
            super("server", MessageType.LOBBY);
            this.usernameList = usernameList;
            this.maxPlayers = maxPlayers;
        }

        public List<String> getUsernameList() {
            return usernameList;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        @Override
        public String toString() {
            return "LobbyMessage{" +
                    "username=" + getUsername() +
                    ", usernameList=" + usernameList +
                    ", numPlayers=" + maxPlayers +
                    '}';
        }

    }

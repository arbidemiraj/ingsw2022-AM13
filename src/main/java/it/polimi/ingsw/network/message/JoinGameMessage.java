package it.polimi.ingsw.network.message;

/**
 *  This message is sent from the client to the server when the user wants to join a game
 */

public class JoinGameMessage extends Message {

    private int gameId;

    public JoinGameMessage(String username, int gameId) {
        super(username, MessageType.JOIN_GAME);
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }



    @Override
    public String toString() {
        return "JoinGameMessage{" +
                "username=" + getUsername() +
                ", gameId=" + gameId +
                '}';
}

    }

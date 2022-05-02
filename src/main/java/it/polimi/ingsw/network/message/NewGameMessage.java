package it.polimi.ingsw.network.message;

public class NewGameMessage extends Message{

    private int maxPlayers;

    private boolean expertMode;

    public NewGameMessage(String username, int maxPlayers, boolean expertMode) {
        super(username, MessageType.NEW_GAME);
        this.maxPlayers = maxPlayers;
        this.expertMode = expertMode;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    @Override
    public String toString() {
        return "NewGameMessage{" +
                "username=" + getUsername() +
                ", numPlayers=" + maxPlayers +
                ", expertMode=" + expertMode +
                '}';
    }
}

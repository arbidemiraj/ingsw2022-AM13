package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class GameInfo extends Message {

    private static final long serialVersionUID = 7982093595616171756L;
    private String info;

    public GameInfo(String info) {
        super("server", MessageType.GAME_INFO);
        this.info =info;
    }

    @Override
    public String toString() {
        return info;
    }
}

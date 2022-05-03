package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class GameInfo extends Message {

    private GameInfo gameInfo;
    public GameInfo (String game, GameInfo gameInfo){
        super(game, MessageType.GAME_INFO);
    }

    @Override
    public String toString() {
        return "Current Player" + getUsername() +
                "\n Current active Player" + "getPlayers()" +
                "\n Activated Characters"+  "getCharacters()"+
                '}';
    }
}

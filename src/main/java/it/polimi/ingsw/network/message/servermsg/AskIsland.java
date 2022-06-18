package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskIsland extends Message{

    private static final long serialVersionUID= -8277246238340056096L;
    private int effectId;

    public AskIsland (int effectId){
        super("server", MessageType.SELECT_ISLAND);
        this.effectId = effectId;
    }

    public int getEffectId() {
        return effectId;
    }

    public String toString(){
        return "";
    }
}

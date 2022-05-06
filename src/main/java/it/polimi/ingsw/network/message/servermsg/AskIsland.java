package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class AskIsland extends Message{

    private static final long serialVersionUID= -8277246238340056096L;

    public AskIsland (){
        super("server", MessageType.SELECT_ISLAND);
    }

    public String toString(){
        return "Select the island where you want to move the student";
    }
}

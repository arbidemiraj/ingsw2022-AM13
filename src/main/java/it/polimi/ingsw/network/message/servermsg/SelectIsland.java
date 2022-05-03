package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class SelectIsland extends Message {
     public SelectIsland(){
        super("server", MessageType.SELECT_ISLAND);
     }

     @Override
     public String toString(){
         return "You have to choose an island ";
     }
}

package it.polimi.ingsw.network.message;

public class AskIslandMessage extends Message{
     public AskIslandMessage(){
        super("game", MessageType.SELECT_ISLAND);
     }

     @Override
     public String toString(){
         return "you can move these students" +
                 '}';
     }
}

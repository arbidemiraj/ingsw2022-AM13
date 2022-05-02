package it.polimi.ingsw.network.message;

public class StartTurnMessage extends Message{
    public StartTurnMessage(){
        super("game", MessageType.START_TURN);
    }

    @Override
    public String toString(){
        return "action or preparation" + '}';
    }
}

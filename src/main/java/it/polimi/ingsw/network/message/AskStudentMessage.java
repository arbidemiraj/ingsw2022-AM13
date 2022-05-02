package it.polimi.ingsw.network.message;

public class AskStudentMessage extends Message{
    public AskStudentMessage(){
        super("game", MessageType.CHOOSE);
    }

    @Override
    public String toString(){
        return "you can move these students" +
                "this is the number of them" +
                '}';
    }
}
package it.polimi.ingsw.network.message.clientmsg;


import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ChooseStudent extends Message {
    public ChooseStudent(String username){
        super(username, MessageType.CHOOSE_STUDENT);
    }

    @Override
    public String toString(){
        return "you can move these students" +
                "this is the number of them" +
                '}';
    }
}
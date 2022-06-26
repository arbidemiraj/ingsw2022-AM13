package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

public class CreateOrJoinAnswer extends Message {

    @Serial
    private static final long serialVersionUID = -5050771524524036011L;
    private final int choice;

    public CreateOrJoinAnswer(String username, int choice){
        super(username, MessageType.CREATE_JOIN_ANSWER);
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }

    @Override
    public String toString() {
        return "CreateOrJoinAnswer{" +
                "choice=" + choice +
                '}';
    }
}

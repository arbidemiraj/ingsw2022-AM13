package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.io.Serial;

/**
 * This message is sent from the client to the server and contains the choice between creating or joining a game
 */
public class CreateOrJoinAnswer extends Message {

    @Serial
    private static final long serialVersionUID = -5050771524524036011L;
    private final int choice;

    /**
     * Default constructor
     * @param username the username of the client
     * @param choice 1 if the players wants to create a game, 2 if he wants to join
     */
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

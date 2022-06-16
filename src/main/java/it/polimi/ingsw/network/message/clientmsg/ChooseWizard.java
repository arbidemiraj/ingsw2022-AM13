package it.polimi.ingsw.network.message.clientmsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

public class ChooseWizard extends Message {
    public ChooseWizard(String username, MessageType messageType) {
        super(username, messageType);
    }
}

package it.polimi.ingsw.network.message.servermsg;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;

import java.util.List;

public class AskWizard extends Message {
    private List<Integer> availableWizards;

    public AskWizard(List<Integer> availableWizards) {
        super("Server", MessageType.ASK_WIZARD);
        this.availableWizards = availableWizards;
    }

    public List<Integer> getAvailableWizards() {
        return availableWizards;
    }

    @Override
    public String toString() {
        return "AskWizard{" +
                "availableWizards=" + availableWizards +
                '}';
    }
}

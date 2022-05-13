package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.clientmsg.DisconnectMessage;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.List;

public class VirtualView implements View{

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askExpertMode() {
        clientHandler.sendMessage(new GenericMessage("Insert \n[1] for expert mode ON \n[2] for expert mode OFF "));
    }

    @Override
    public void askNumberOfPlayers() {
        clientHandler.sendMessage(new GenericMessage("Insert number of players: "));
    }

    @Override
    public void askTowerColor() {
        clientHandler.sendMessage(new AskTowerColor());
    }

    @Override
    public void successMessage() {
        clientHandler.sendMessage(new SuccessMessage());
    }

    @Override
    public void disconnectionMessage() {
        clientHandler.sendMessage(new Disconnection());
    }

    @Override
    public void error(String error) {
        clientHandler.sendMessage(new ErrorMessage(error));
    }

    @Override
    public void showLobby(String lobby) {
        clientHandler.sendMessage(new LobbyMessage(lobby));
    }

    @Override
    public void winMessage() {
        clientHandler.sendMessage(new WinMessage());
    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards) {
        clientHandler.sendMessage(new AskCard(assistantCards));
    }

    @Override
    public void askCloud() {
        clientHandler.sendMessage(new ChooseMessage());
    }

    @Override
    public void askStudentToMove() {
        clientHandler.sendMessage(new AskStudent());
    }

    @Override
    public void askIslandToMove() {
        clientHandler.sendMessage(new AskIsland());
    }
}

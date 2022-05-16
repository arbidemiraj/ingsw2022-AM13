package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.network.message.ErrorType;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.List;

public class VirtualView implements View, Observer {

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askCreateOrJoin() {
        clientHandler.sendMessage(new ChooseMessage());
    }

    @Override
    public void askUsername() {

    }

    @Override
    public void askGameSettings() {

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
        clientHandler.sendMessage(new ErrorMessage(error, ErrorType.GENERIC));
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

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.CreateOrJoinAnswer;
import it.polimi.ingsw.network.message.clientmsg.JoinGameMessage;
import it.polimi.ingsw.network.message.clientmsg.LoginMessage;
import it.polimi.ingsw.network.message.clientmsg.NewGameMessage;
import it.polimi.ingsw.network.message.servermsg.ChooseMessage;
import it.polimi.ingsw.network.message.servermsg.ErrorMessage;
import it.polimi.ingsw.network.message.servermsg.LobbyMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements ViewObserver, Observer {

    private final View view;
    private Client client;
    private String username;

    private final ExecutorService taskQueue;

    public ClientController(View view){
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }


    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case ERROR -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                switch(errorMessage.getErrorType()){
                    case DUPLICATE_USERNAME -> taskQueue.execute(view::askUsername);
                }
            }
            case CHOOSE_GAME_OPTIONS -> {
                taskQueue.execute(view::askCreateOrJoin);
            }
            case ASK_GAME_SETTINGS -> {
                taskQueue.execute(view::askGameSettings);
            }
            case LOBBY -> {
                LobbyMessage lobbyMessage = (LobbyMessage) message;
                String s = lobbyMessage.toString();
                taskQueue.execute(() -> view.showLobby(s));
            }

        }
    }

    @Override
    public void onUpdateServerInfo(List<String> serverInfo) {

        try {

            client = new SocketClient(serverInfo.get(0), Integer.parseInt(serverInfo.get(1)));
            client.addObserver(this);
            client.readMessage();
            client.enablePing(true);
            taskQueue.execute(view::askUsername);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateCreateOrJoin(int choice) {
        client.sendMessage(new CreateOrJoinAnswer(username, choice));
    }

    @Override
    public void onUpdateCharacter(int effectId) {

    }

    @Override
    public void onUpdateCloud(int cloudId) {

    }

    @Override
    public void onUpdateTowerColor(TowerColor chosenTowerColor) {

    }

    @Override
    public void onUpdateDisconnect() {

    }

    @Override
    public void onUpdateIslandEffect(Island chosenIsland) {

    }

    @Override
    public void onUpdateJoinGame(int gameId) {
        client.sendMessage(new JoinGameMessage(username, gameId));
    }

    @Override
    public void onUpdateLoginMessage(String username) {
        this.username = username;
        client.sendMessage(new LoginMessage(username));
    }

    @Override
    public void onUpdateMotherNature(int steps) {

    }

    @Override
    public void onUpdateStudent(Movable from, Student color, Movable to) {

    }

    @Override
    public void onUpdateNewGame(int maxPlayers, boolean expertMode) {
        client.sendMessage(new NewGameMessage(username, maxPlayers, expertMode));
    }

    @Override
    public void onUpdateCard(int assistantcardId) {

    }

    @Override
    public void onUpdateReloadGame() {

    }

    @Override
    public void onUpdateStudentEffect(Student chosenStudent) {

    }
}

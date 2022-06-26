package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.*;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements ViewObserver, Observer {

    private final View view;
    private Client client;
    private String username;
    private ReducedModel reducedModel;

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
                String error = errorMessage.getError();

                taskQueue.execute(() -> view.error(error));
                switch(errorMessage.getErrorType()){
                    case DUPLICATE_USERNAME -> {
                        taskQueue.execute(view::askUsername);
                    }

                    case DUPLICATE_CARD -> {
                        taskQueue.execute(() -> view.error(error));
                    }
                }
            }
            case GENERIC -> {
                GenericMessage genericMessage = (GenericMessage) message;
                taskQueue.execute(() -> view.showGenericMessage(genericMessage.toString()));
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

            case ASK_MN -> {
                taskQueue.execute(view::askMotherNatureMove);
            }
            case START_GAME -> {
                StartGame msg = (StartGame) message;
                this.reducedModel = msg.getReducedModel();

                taskQueue.execute(() -> view.startGame(msg.getFirstPlayer(), msg.getReducedModel()));
            }

            case TOWER_COLOR_ASK -> {
                AskTowerColor askTowerColor = (AskTowerColor) message;

                taskQueue.execute(() -> view.askTowerColor(askTowerColor.getAvailableColors()));
            }

            case ASK_CARD -> {
                AskCard msg = (AskCard) message;
                taskQueue.execute(() -> view.askCardToPlay(msg.getAssistantCards(), msg.getCardsPlayed()));
            }

            case BOARD_MESSAGE -> {
                BoardMessage msg = (BoardMessage) message;

                taskQueue.execute(() -> view.setBoard(msg.getReducedBoard()));
            }

            case ASK_CLOUD -> {
                taskQueue.execute(view::askCloud);
            }

            case REDUCED_MODEL -> {
                ReducedModelMessage msg = (ReducedModelMessage) message;

                taskQueue.execute(()-> view.createModel(msg.getReducedModel()));
            }

            case ASK_STUDENT -> {
                AskStudent msg = (AskStudent) message;

                taskQueue.execute(view::askStudentToMove);
            }

            case TURN_INFO -> {
                TurnInfo turnInfo = (TurnInfo) message;

                taskQueue.execute(() -> view.setTurnInfo(turnInfo.getSteps()));
            }

            case CONFIRM_CHARACTER -> {
                ConfirmCharacterActivation msg = (ConfirmCharacterActivation) message;

                int id = msg.getId();

                taskQueue.execute(() -> view.activateCharacter(id));
            }

            case WIN -> {
                WinMessage msg = (WinMessage) message;

                taskQueue.execute(() -> view.winMessage(msg.getWinner()));
            }

            case UPDATE_CLOUD -> {
                UpdateClouds msg = (UpdateClouds) message;

                taskQueue.execute(() -> view.updateClouds(msg.getCloudId()));
            }

            case FILL_CLOUDS -> {

                FillCloudsMessage msg = (FillCloudsMessage) message;

                taskQueue.execute(() -> view.fillClouds(msg.getClouds()));
            }

            case STUDENTS -> {
                StudentsMessage msg = (StudentsMessage) message;


                taskQueue.execute(() -> view.askStudentEffect(msg.getEffectId()));
            }

            case UPDATE_MOTHERNATURE -> {
                UpdateMotherNature msg = (UpdateMotherNature) message;

                taskQueue.execute(() -> view.setMotherNature(msg.getMotherNature()));
            }

            case ASK_SWITCH_STUDENT -> {
                taskQueue.execute(view::askSwitch);
            }

            case SELECT_ISLAND -> {
                AskIsland msg = (AskIsland) message;

                taskQueue.execute(() -> view.askIslandEffect(msg.getEffectId()));
            }

            case EFFECT12 -> {
                Effect12Message msg = (Effect12Message) message;


                taskQueue.execute(() -> view.askEffect12Students(msg.getColor()));
            }

            case UPDATE_CHARACTER_STUDENTS -> {
                UpdateCharacterStudents msg = (UpdateCharacterStudents) message;

                taskQueue.execute(() -> view.updateCharacterStudents(msg.getStudents(), msg.getEffectId()));
            }

            case CHARACTER_ACTIVATED -> {
                CharacterActivated msg = (CharacterActivated) message;

                taskQueue.execute(() -> view.notifyCharacterActivation(msg.getEffectId(), msg.isActivated(), msg.getOwner()));
            }
            case UPDATE_MODEL -> {
                UpdateModelMessage msg = (UpdateModelMessage) message;

                switch (msg.getUpdateType()){
                    case TURN_CARDS -> {
                        taskQueue.execute(() -> view.updateModel(msg.getTurnCardsPlayed()));
                    }

                    case MAX_STEPS -> reducedModel.setMaxSteps(msg.getMaxSteps());

                    case PROFESSOR -> {
                        taskQueue.execute(() -> view.changeProfOwner(msg.getProfessorOwner(), msg.getColor()));
                    }

                    case MERGE -> {
                        taskQueue.execute(() -> view.mergeIsland(msg.getIsland1(), msg.getIsland2()));
                    }

                    case CONQUER -> {
                        taskQueue.execute(() -> view.conquerIsland(msg.getIsland(), msg.getTowerColor()));
                    }

                    case ISLANDS -> {
                        taskQueue.execute(() -> view.updateIslands(msg.getIslands()));
                    }
                }
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
        client.sendMessage(new ActivateCharacterMessage(username, effectId));
    }

    @Override
    public void onUpdateCloud(int cloudId) {
        client.sendMessage(new ChooseCloudMessage(username, cloudId));
    }

    @Override
    public void onUpdateTowerColor(String chosenTowerColor) {
        TowerColor sendTowerColor = TowerColor.valueOf(chosenTowerColor);
        client.sendMessage(new ChooseTowerColorMessage(username, sendTowerColor));
    }

    @Override
    public void onUpdateDisconnect() {
        client.sendMessage(new DisconnectMessage(username));
    }

    @Override
    public void onUpdateIslandEffect(int chosenIsland, int effectId) {
        client.sendMessage(new IslandEffectMessage(username, chosenIsland, effectId));
    }

    @Override
    public void onUpdateIslandEffect(int chosenIsland) {
    }

    @Override
    public void onUpdateJoinGame(int gameId) {
        client.sendMessage(new JoinGameMessage(username, gameId));
    }

    @Override
    public void onUpdateLoginMessage(String username) {
        this.username = username;
        taskQueue.execute(() -> view.setPlayerUsername(username));

        client.sendMessage(new LoginMessage(username));
    }

    @Override
    public void onUpdateMotherNature(int steps) {
        client.sendMessage(new MoveMotherNatureMessage(username, steps));

        taskQueue.execute(() -> view.updateMotherNature(steps));
    }

    @Override
    public void onUpdateStudent(String from, String color, String to, int id) {
        client.sendMessage(new MoveStudentMessage(username, from, color, to, id));
    }

    @Override
    public void onUpdateNewGame(int maxPlayers, boolean expertMode) {
        client.sendMessage(new NewGameMessage(username, maxPlayers, expertMode));
    }

    @Override
    public void onUpdateCard(int assistantCardId) {
        client.sendMessage(new PlayCardMessage(username, assistantCardId));
    }

    @Override
    public void onUpdateReloadGame() {

    }

    @Override
    public void onUpdateStudentEffect(String chosenStudent) {

    }

    @Override
    public void onUpdateStudentEffect(String chosenStudent, int effectId) {
        client.sendMessage(new StudentEffectMessage(username, Student.valueOf(chosenStudent), effectId));
    }

    @Override
    public void onUpdateSwitchStudents(ArrayList<Student> students){
        client.sendMessage(new SwitchStudents(username, students));
    }
}

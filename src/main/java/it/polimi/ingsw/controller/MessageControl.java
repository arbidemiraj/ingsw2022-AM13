package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.exceptions.InvalidMotherNatureMovesException;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.*;

import java.io.Serializable;

public class MessageControl implements Serializable {
    private static final long serialVersionUID = 1321929544794429579L;

    private final Controller controller;

    public MessageControl(Controller controller){
        this.controller = controller;
    }
    public void messageRecieved (Message message){
        switch (message.getMessageType()) {

            case ACTIVATE_CHARACTER -> {
                ActivateCharacterMessage activateCharacterMessage = (ActivateCharacterMessage) message;
                //controller.activateStudentCharacter(activateCharacterMessage.getEffectId());
            }

            case CLOUD -> {
                ChooseCloudMessage chooseCloudMessage = (ChooseCloudMessage) message;
                controller.moveStudentsFromCloud (chooseCloudMessage);
            }

            case TOWER_COLOR_CHOOSE -> {
                TowerColorMessage towerColorMessage = (TowerColorMessage) message;
                controller.getGame().getPlayerByUsername(towerColorMessage.getUsername())
                        .setTowerColor(towerColorMessage.getChosenTowerColor());
            }
            case DISCONNECTED -> {
                DisconnectMessage disconnectMessage = (DisconnectMessage) message;
            }
            case GAME_INFO -> {
                GameInfo gameInfo = (GameInfo) message;
                controller.getGame();
            }

            case ISLAND_EFFECT -> {
                IslandEffectMessage islandEffectMessage = (IslandEffectMessage) message;
                //controller.activateIslandCharacter (islandEffectMessage.getChosenIsland());
            }

            case MOVE_MOTHERNATURE -> {
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) message;
                try {
                    controller.moveMotherNature (moveMotherNatureMessage.getSteps());
                } catch (InvalidMotherNatureMovesException e) {
                    throw new RuntimeException(e);
                }
            }

            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                controller.moveStudent (studentMessage.getFrom(),studentMessage.getColor(),studentMessage.getTo());
            }

            case PLAY_CARD -> {
                PlayCardMessage playCardMessage = (PlayCardMessage) message;
                //controller.playCard (playCardMessage.getAssistantcardId());
            }
        }
    }
}

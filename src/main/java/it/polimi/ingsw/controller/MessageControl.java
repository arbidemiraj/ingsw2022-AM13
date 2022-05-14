package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientmsg.*;

import java.io.Serializable;
/*
public class MessageControl implements Serializable {
    private static final long serialVersionUID = 1321929544794429579L;

    public void messageRecieved (Message message){
        switch (message.getMessageType()) {
            case ACTIVATE_CHARACTER -> {
                ActivateCharacterMessage activateCharacterMessage = (ActivateCharacterMessage) message;
                controller.activateStudentCharacter(activateCharacterMessage.getEffectId(),activateCharacterMessage.getUsername());
            }
            case CLOUD -> {
                ChooseCloudMessage chooseCloudMessage = (ChooseCloudMessage) message;
                controller.moveStudentsFromCloud (chooseCloudMessage.getCloudId());
            }

            case TOWER_COLOR_CHOOSE -> {
                TowerColorMessage towerColorMessage = (TowerColorMessage) message;
                controller.towerColor (towerColorMessage.getChosenTowerColor());
            }
            case DISCONNECTED -> {
                DisconnectMessage disconnectMessage = (DisconnectMessage) message;
                controller.getUsername();

            }
            case GAME_INFO -> {
                GameInfo gameInfo = (GameInfo) message;
                controller.getGame(gameInfo.getUsername());
            }

            case ISLAND_EFFECT -> {
                IslandEffectMessage islandEffectMessage = (IslandEffectMessage) message;
                getChoosenIsland (islandEffectMessage.getChosenIsland(message));
            }

            case MOVE_MOTHERNATURE -> {
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) message;
                controller.moveMotherNature (moveMotherNatureMessage.getSteps());
            }

            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                controller.moveStudent (studentMessage.getFrom(),studentMessage.getColor(),studentMessage.getTo());
            }
            case SELECT_ISLAND->{}
            case PLAY_CARD -> {}
            case STUDENT_EFFECT -> {}
            case TOWER_COLOR -> {}
        }
    }
}*/
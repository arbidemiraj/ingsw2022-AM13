package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientmsg.MoveStudentMessage;
import it.polimi.ingsw.network.message.clientmsg.TowerColorMessage;

import java.io.Serializable;

public class MessageControl implements Serializable {
    private static final long serialVersionUID = 1321929544794429579L;

    public void messageRecieved (Message message){
        switch (message.getMessageType()) {
            case ACTIVATE_CHARACTER -> {}
            case CLOUD -> {}
            case CHOOSE_STUDENT -> {}
            case TOWER_COLOR_CHOOSE -> {
                TowerColorMessage towerColorMessage = (TowerColorMessage) message;
                //Controller.towerColor (towerColorMessage.getChosenTowerColor());
            }
            case DISCONNECTED -> {}
            case GAME_INFO -> {}
            case ISLAND_EFFECT -> {}
            case MOVE_MOTHERNATURE -> {}
            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                Controller.moveStudent (studentMessage.getFrom(),studentMessage.getColor(),studentMessage.getTo());
            }
            case SELECT_ISLAND->{}
            case PLAY_CARD -> {}
            case STUDENT_EFFECT -> {}
            case TOWER_COLOR -> {}
        }
    }
}

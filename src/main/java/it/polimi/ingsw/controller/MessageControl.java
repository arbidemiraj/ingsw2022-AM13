package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientmsg.MoveStudentMessage;

import java.io.Serializable;

public class MessageControl implements Serializable {
    private static final long serialVersionUID = 1321929544794429579L;

    //private final Game game;

    public boolean controlMsgRecieved (Message message){
        switch (message.getMessageType()) {
            case LOGIN_REQUEST:
                break;
            case CHOOSE:
                break;
            case TOWER_COLOR_CHOOSE:
                break;
            case TOWER_COLOR:
                break;
            case SELECT_ISLAND:
                break;
            case START_GAME:
                break;
            case INPUT:
                break;
            case MOVE_STUDENT:
                return move(message);
            case DISCONNECTED:
                break;
            case NEW_GAME:
                return startGame(message);
            case GAME_INFO:
                break;
            case JOIN_GAME:
                return addPlayer(message);
            case LOAD_GAME:
                break;
            case MOVE_MOTHERNATURE:
                break;
            case PLAY_CARD:
                break;
            case ACTIVATE_CHARACTER:
                break;
            default://in case of errors
                return false;
        }
    return false;
    }

    public boolean move(Message message){

        if(message.getMessageType() == MessageType.MOVE_STUDENT){
            MoveStudentMessage moveStudent = ((MoveStudentMessage)message);
            return true;
        } else {
            return false;
        }
    }
    public boolean startGame(Message message){
        if(message.getMessageType() == MessageType.NEW_GAME){

        } else {
            return false;
        }
        return false;
    }
    public boolean addPlayer(Message message){
        if(message.getMessageType() == MessageType.JOIN_GAME){

        } else {
            return false;
        }
        return false;
    }
    /*private boolean moveCheck(Message message) {
        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        PositionMessage positionMessage = ((PositionMessage) message);
        Position chosenDest = positionMessage.getPositionList().get(0);
        List<Position> possibleMovePositions = gameController.getTurnController().getActiveWorker().getPossibleMoves();

        if (!positionMessage.getPositionList().isEmpty() && possibleMovePositions.contains(chosenDest)) {
            return true;
        } else {
            virtualView.showGenericMessage("You didn't provided a valid Destination. Retry.");
            virtualView.askMove(gameController.getTurnController().getActiveWorker().getPossibleMoves());
            return false;
        }
    }*/
}

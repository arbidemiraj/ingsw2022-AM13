package it.polimi.ingsw.network.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST,
    CHOOSE,
    SUCCESS,
    LOGIN_REPLY,
    LOBBY,
    PICK_FIRST_PLAYER,
    BOARD,
    GAME_TABLE,
    INIT_COLORS,
    TOWER_COLOR,
    START_TURN,
    SELECT_ISLAND,
    MOVE_STUDENT,
    MOVE_MN,
    WIN,
    LOSE,


    NEW_GAME,
    LOAD_GAME,
    GAME_INFO,
    DISCONNECTED,
    PING,
    ERROR,
    CARD_EFFECT,
    END_TURN,
    JOIN_GAME,
    RELOAD_GAME;
}
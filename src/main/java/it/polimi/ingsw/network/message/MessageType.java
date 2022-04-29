package it.polimi.ingsw.network.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST, LOGIN_REPLY,
    LOBBY,
    PICK_FIRST_PLAYER,
    BOARD,
    GAME_TABLE,
    INIT_COLORS,
    TOWER_COLOR,
    MOVE_STUDENT,
    MOVE_MN,
    MOVE_PROFESSOR,
    WIN,
    LOSE,

    //utility:
    NEW_GAME,
    LOAD_GAME,
    MATCH_INFO,
    DISCONNECTED,
    GENERIC_MESSAGE,
    PING,
    ERROR,
    CARD_EFFECT
}
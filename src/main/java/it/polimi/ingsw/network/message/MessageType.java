package it.polimi.ingsw.network.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST,
    CHOOSE,
    SUCCESS,
    TOWER_COLOR_CHOOSE,
    TOWER_COLOR_ASK,
    LOGIN_REPLY,
    LOBBY,
    PICK_FIRST_PLAYER,
    BOARD,
    GAME_TABLE,
    INIT_COLORS,
    TOWER_COLOR,
    START_TURN,
    SELECT_ISLAND,
    START_GAME,
    INPUT,
    MOVE_STUDENT,
    MOVE_MN,
    WIN,
    LOSE,
    CLOUD,
    NEW_GAME,
    LOAD_GAME,
    GAME_INFO,
    DISCONNECTED,
    PING,
    ERROR,
    CARD_EFFECT,
    END_TURN,
    JOIN_GAME,
    RELOAD_GAME,
    MOVE_MOTHERNATURE,
    PLAY_CARD,
    ACTIVATE_CHARACTER,
    ISLAND_EFFECT,
    STUDENT_EFFECT;
}
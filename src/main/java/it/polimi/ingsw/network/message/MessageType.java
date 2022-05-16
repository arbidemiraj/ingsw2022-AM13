package it.polimi.ingsw.network.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST,
    CHOOSE_STUDENT,
    GENERIC,
    CHOOSE_GAME_OPTIONS,
    ASK_GAME_SETTINGS,
    ASK_CARD,
    SUCCESS,
    TOWER_COLOR_CHOOSE,
    TOWER_COLOR_ASK,
    LOGIN_REPLY,
    LOBBY,
    ASK_STUDENT,
    TOWER_COLOR,
    START_TURN,
    SELECT_ISLAND,
    START_GAME,
    INPUT,
    MOVE_STUDENT,
    WIN,
    CLOUD,
    NEW_GAME,
    END_GAME,
    GAME_INFO,
    DISCONNECTED,
    PING,
    ERROR,
    END_TURN,
    JOIN_GAME,
    RELOAD_GAME,
    MOVE_MOTHERNATURE,
    PLAY_CARD,
    ACTIVATE_CHARACTER,
    ISLAND_EFFECT,
    STUDENT_EFFECT,
    LOAD_GAME, CREATE_JOIN_ANSWER;
}
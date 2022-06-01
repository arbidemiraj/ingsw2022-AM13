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
    ASK_CLOUD,
    ASK_CARD,
    SUCCESS,
    TOWER_COLOR_CHOOSE,
    TOWER_COLOR_ASK,
    LOBBY,
    ASK_STUDENT,
    START_TURN,
    SELECT_ISLAND,
    START_GAME,
    START_SETUP,
    BOARD_MESSAGE,
    ASK_MN,
    REDUCED_MODEL,
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
    CREATE_JOIN_ANSWER, TURN_INFO, CONFIRM_CHARACTER, CONFIRM_STUDENT_MOVE, UPDATE_MOVE, UPDATE_MODEL;
}
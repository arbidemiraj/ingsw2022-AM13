package it.polimi.ingsw.network.message;

/**
 * Enum with the different error message type
 */
public enum ErrorType {
    DUPLICATE_USERNAME,
    DISCONNECTION,
    CONNECTION_LOST,
    DUPLICATE_CARD,
    INVALID_MOVE,
    GENERIC, NOT_ENOUGH_COINS, INVALID_SWITCH, INVALID_GAME_ID;
}

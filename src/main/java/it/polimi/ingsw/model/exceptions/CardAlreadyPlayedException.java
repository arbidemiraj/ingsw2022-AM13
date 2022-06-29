package it.polimi.ingsw.model.exceptions;

/**
 * Thrown when a card has already been played
 */
public class CardAlreadyPlayedException extends Exception{
    @Override
    public String getMessage() {
        return "Card already played! You have to play another card";
    }
}

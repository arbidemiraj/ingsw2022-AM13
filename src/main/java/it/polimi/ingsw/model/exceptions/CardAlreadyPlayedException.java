package it.polimi.ingsw.model.exceptions;

public class CardAlreadyPlayedException extends Exception{
    @Override
    public String getMessage() {
        return "Card already played! You have to play another card";
    }
}

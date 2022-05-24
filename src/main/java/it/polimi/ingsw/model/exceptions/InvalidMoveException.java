package it.polimi.ingsw.model.exceptions;

public class InvalidMoveException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid move!";
    }
}

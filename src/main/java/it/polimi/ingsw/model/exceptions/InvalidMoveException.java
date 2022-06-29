package it.polimi.ingsw.model.exceptions;

/**
 * Thrown when an invalid move of students has been made
 */
public class InvalidMoveException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid move!";
    }
}

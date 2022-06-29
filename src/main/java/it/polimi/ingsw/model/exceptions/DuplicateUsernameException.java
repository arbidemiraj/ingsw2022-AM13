package it.polimi.ingsw.model.exceptions;

/**
 * Throw when a username has already been taken
 */
public class DuplicateUsernameException extends Exception{
    private String error;

    public DuplicateUsernameException() {
        error = "The username has already been taken";
    }

    public String getError() {
        return error;
    }
}

package it.polimi.ingsw.model.exceptions;

public class InvalidGameIdException extends Exception{
    public void printError(){
        System.out.println("Invalid id");
    }
}

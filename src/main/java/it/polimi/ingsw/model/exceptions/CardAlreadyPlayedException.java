package it.polimi.ingsw.model.exceptions;

public class CardAlreadyPlayedException extends Exception{
    public void printError(){
        System.out.println("Card already played");
    }
}

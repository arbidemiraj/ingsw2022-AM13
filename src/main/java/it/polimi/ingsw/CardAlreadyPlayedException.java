package it.polimi.ingsw;

public class CardAlreadyPlayedException extends Exception{
    public void printError(){
        System.out.println("Card already played");
    }
}

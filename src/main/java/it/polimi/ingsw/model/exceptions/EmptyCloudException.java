package it.polimi.ingsw.model.exceptions;

public class EmptyCloudException extends Exception{
    public void printError(){
        System.out.println("Cloud is empty");
    }
}

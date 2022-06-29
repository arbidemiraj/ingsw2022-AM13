package it.polimi.ingsw.model.exceptions;

/**
 * Thrown when the cloud is empty
 */
public class EmptyCloudException extends Exception{
    public void printError(){
        System.out.println("Cloud is empty");
    }
}

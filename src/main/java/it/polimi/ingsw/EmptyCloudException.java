package it.polimi.ingsw;

public class EmptyCloudException extends Exception{
    public void printError(){
        System.out.println("Cloud is empty");
    }
}

package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientApp;
import it.polimi.ingsw.network.ServerApp;
import it.polimi.ingsw.view.gui.JavaFxMain;

import java.io.IOException;
import java.util.Scanner;

public class Eriantys {
    public static void main(String[] args) {
        System.out.println("Welcome to Eriantys! What do you want to launch? ");
        System.out.println("[1] SERVER  [2] CLIENT (CLI INTERFACE) [3] CLIENT (GUI INTERFACE)");

        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();;

        switch (input){
            case 1 -> ServerApp.main(null);
            case 2 -> {
                try {
                    ClientApp.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                JavaFxMain.main(null);
            }
            default -> {
                System.out.println("Invalid argument! Please rerun the executable");
            }
        }
    }
}

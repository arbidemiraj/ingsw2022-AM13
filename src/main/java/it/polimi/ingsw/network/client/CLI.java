package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI extends ViewObservable implements View {
    private Scanner input = new Scanner(System.in);
    private final PrintStream output;
    private String username;

    private static final String INV_INP = "Invalid input! ";

    public CLI(){
        output = System.out;
    }
    public void init(){
        output.println(" ________           _                   _                   \n" +
                "|_   __  |         (_)                 / |_                 \n" +
                "  | |_ \\_| _ .--.  __   ,--.   _ .--. `| |-' _   __  .--.   \n" +
                "  |  _| _ [ `/'`\\][  | `'_\\ : [ `.-. | | |  [ \\ [  ]( (`\\]  \n" +
                " _| |__/ | | |     | | // | |, | | | | | |,  \\ '/ /  `'.'.  \n" +
                "|________|[___]   [___]\\'-;__/[___||__]\\__/[\\_:  /  [\\__) ) \n" +
                "                                            \\__.'           ");

        output.println("Welcome to Eriantys!");


        askServerInfo();

    }

    public void askServerInfo(){
        ArrayList<String> serverInfo = new ArrayList<>();
        String defaultAddress = "localhost";
        String defaultPort = "12345";
        boolean isValid;

        do{
            output.print("\n\nEnter the server address [ Default = " + defaultAddress + "] : ");
            String address = input.nextLine();

            if(address.equals("")){
                serverInfo.add(defaultAddress);
                isValid = true;
            } else{
                output.println("Invalid address");
                isValid = false;
            }

        }while(!isValid);

        do{
            output.print("Enter the server port [ Default = " + defaultPort + "] : ");
            String address = input.nextLine();

            if(address.equals("")){
                serverInfo.add(defaultPort);
                isValid = true;
            } else{
                output.println("Invalid port");
                isValid = false;
            }

        }while(!isValid);

        notifyObserver(viewObserver -> viewObserver.onUpdateServerInfo(serverInfo));

    }

    @Override
    public void askUsername(){
        printLine();
        output.println("\nEnter username: ");
        output.print("> ");

        username = input.nextLine();

        notifyObserver(obs -> obs.onUpdateLoginMessage(username));
    }

    @Override
    public void askCreateOrJoin(){
        int choice;
        boolean isValid;


        do{
            output.println("\n\nWelcome " + username + " !");
            output.println("\n\n [1] to create a new game \n [2] to join a game\n");

            choice = input.nextInt();

            if(choice == 1 || choice == 2) isValid = true;
            else{
                isValid = false;
                output.println(INV_INP);
            }
        }while(!isValid);


        int finalChoice = choice;

        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(finalChoice));
    }

    @Override
    public void askGameSettings() {
        boolean isValid;
        int numPlayers;

        do{
            output.println("\nInsert number of players [ 2 or 3 ]");
            output.print("> ");

            numPlayers = input.nextInt();

            if(numPlayers == 2 || numPlayers == 3) isValid = true;
            else {
                isValid = false;
                output.println(INV_INP);
            }
        }while(!isValid);

        int expertMode;

        do{
            output.println("\nInsert \n[1] for expert mode ON \n[2] for expert mode OFF ");
            output.printf("> ");
            expertMode = input.nextInt();

            if(expertMode == 1 || expertMode == 2) isValid = true;
            else {
                isValid = false;
                output.println(INV_INP);
            }
        }while(!isValid);




        boolean expertModeBoolean = false;

        if(expertMode == 1) expertModeBoolean = true;

        boolean finalExpertModeBoolean = expertModeBoolean;

        int finalNumPlayers = numPlayers;

        notifyObserver(viewObserver -> viewObserver.onUpdateNewGame(finalNumPlayers, finalExpertModeBoolean));

        output.println("\nWaiting for players to join... ");
    }


    @Override
    public void askTowerColor() {
        String chosenTowerColor;
        boolean isValid;

        output.println("\nYou have to choose a tower color [BLACK | GRAY | WHITE]: ");
        output.print("> ");

        do{
            output.println("\nYou have to choose a tower color [BLACK | GRAY | WHITE]: ");
            output.print("> ");

            chosenTowerColor = input.nextLine();

            if( chosenTowerColor.equals("BLACK") || chosenTowerColor.equals("WHITE") || chosenTowerColor.equals("GRAY")) isValid = true;
            else {
                isValid = false;
                output.println(INV_INP);
            }
        }while(!isValid);


        output.println(chosenTowerColor);

        //notifyObserver(viewObserver -> viewObserver.onUpdateTowerColor());
    }

    @Override
    public void successMessage() {

    }

    @Override
    public void disconnectionMessage() {

    }

    @Override
    public void error(String error) {
        output.println("\n" + error);
    }

    @Override
    public void showLobby(String lobby) {
        output.println(lobby);

        output.println("\nInsert the ID of the game you want to join: ");
        output.print("> ");

        int gameId = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateJoinGame(gameId));
    }

    @Override
    public void winMessage() {

    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards) {

    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askStudentToMove() {

    }

    @Override
    public void askIslandToMove() {

    }

    public void clearCli(){
        output.print("\033[H\033[2J");
        output.flush();
    }

    public void printLine(){
        output.println("\n-----------------------------------------------------------");
    }

    public void connectionLost(){
        output.println("\nConnection lost with the server");
    }

    @Override
    public void startGame() {
        output.println("The game is starting...");
    }

    @Override
    public void showGenericMessage(String message) {
        output.println(message);
    }
}

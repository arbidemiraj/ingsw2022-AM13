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
        output.println("\n\nWelcome " + username + " !");
        output.println("\n\n [1] to create a new game \n [2] to join a game\n");

        int choice = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateCreateOrJoin(choice));
    }

    @Override
    public void askGameSettings() {
        output.println("\nInsert number of players");
        output.print("> ");

        int numPlayers = input.nextInt();

        output.println("\nInsert \n[1] for expert mode ON \n[2] for expert mode OFF ");
        output.printf("> ");
        int expertMode = input.nextInt();

        boolean expertModeBoolean = false;

        if(expertMode == 1) expertModeBoolean = true;

        boolean finalExpertModeBoolean = expertModeBoolean;

        notifyObserver(viewObserver -> viewObserver.onUpdateNewGame(numPlayers, finalExpertModeBoolean));

    }


    @Override
    public void askTowerColor() {
    }

    @Override
    public void successMessage() {

    }

    @Override
    public void disconnectionMessage() {

    }

    @Override
    public void error(String error) {

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
}

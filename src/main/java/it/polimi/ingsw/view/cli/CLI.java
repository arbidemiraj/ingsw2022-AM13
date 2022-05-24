package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedCharacter;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class CLI extends ViewObservable implements View {
    private Scanner input = new Scanner(System.in);
    private Color color;
    private final PrintStream output;
    private String status;
    private String username;
    public static final Logger LOGGER = Logger.getLogger(CLI.class.getName());
    private boolean activatingCharacter;
    private ReducedModel reducedModel;

    private final String INV_INP = "Invalid input! ";

    public CLI(){
        output = System.out;
        activatingCharacter = false;
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
    }


    @Override
    public void askTowerColor(List<TowerColor> availableColors) {
        String chosenTowerColor;
        boolean isValid;
        String bug = input.nextLine(); //??????

        do{

            output.println("\nYou have to choose a tower color " + availableColors );
            output.print("> ");


            chosenTowerColor = input.nextLine();

            chosenTowerColor.toUpperCase();
            if( chosenTowerColor.equals("BLACK") || chosenTowerColor.equals("WHITE") || chosenTowerColor.equals("GRAY")){
                isValid = true;
            }
            else {
                isValid = false;
                output.println(INV_INP);
            }
        }while(!isValid);


        String finalChosenTowerColor = chosenTowerColor;

        notifyObserver(viewObserver -> viewObserver.onUpdateTowerColor(finalChosenTowerColor));
    }

    @Override
    public void startTurn() {
    }

    public void createBoard(ReducedBoard reducedBoard){
        this.reducedModel.setReducedBoard(reducedBoard);
    }

    public void showBoard() {
        output.println("\n\n-----------------------------------\n" +
                        "USERNAME: " +
                        reducedModel.getUsername() +
                        "\nCOLOR: " +
                        reducedModel.getColor() +
                        "\n\nPLAYER BOARD\n" +
                        reducedModel.getReducedBoard().printPlayerBoard());

        output.println("\n");
        String infos = "";
        String menu = "";

        infos +="\nType CHARACTER <CharacterId> to activate the effect " +
                "\nType DESC <CharacterId> to see a desc of the character effect" +
                "\n\nCHARACTERS: \n";

        for(ReducedCharacter character : reducedModel.getReducedCharacters()){
            infos +=
                    "\nID: " + character.getEffectId() +
                            "\nCOST: " + character.getCost() +"\n--------\n";

        }

        output.print(infos + "\n\n");

        output.println(reducedModel.getReducedBoard().printIslands());
    }

    public void updateBoard(){

    }
    @Override
    public void successMessage() {
        output.println("Operation successfully completed! ");
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
    public void askCardToPlay(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {

        output.println("\n\nThis is your deck -> " + printDeck(assistantCards));
        output.print("\nThese are the cards that have been played this turn -> ");
        for(AssistantCard assistantCard : cardsPlayed){
            output.print(assistantCard.getValue() + "   ");
        }

        output.print("\n");
        output.println("Choose the card you want to play this turn! ");
        output.println("[ insert the number of the card ]");
        output.print("> ");


        int cardId = Integer.parseInt(readLine());

        notifyObserver(viewObserver -> viewObserver.onUpdateCard(cardId));
    }

    private String printDeck(List<AssistantCard> assistantCards) {
        String deck = "";

        for(AssistantCard assistantCard : assistantCards){
            deck += "\n Card  " + assistantCard.getValue() +
                    " [ V = " + assistantCard.getValue() + " ] " +
                    " [ M = " + assistantCard.getMaxMotherNatureMoves() + " ] ";
        }

        return deck;
    }

    @Override
    public void askCloud() {
        output.println(reducedModel.getReducedBoard().printClouds());

        output.println("Choose the cloud you want to get the students from");
        output.println("[ insert the number of the cloud ]");
        output.print("> ");

        int cloud = 0;

        String read = readLine();

        if(read == null){
            activatingCharacter = true;
        }
        else{
            cloud = input.nextInt();
        }

        int finalCloud = cloud;
        notifyObserver(viewObserver -> viewObserver.onUpdateCloud(finalCloud-1));
    }

    @Override
    public void askStudentToMove(int numStudents) {
        String[] from = new String[numStudents];
        String[] student = new String[numStudents];
        String[] to = new String[numStudents];
        int[] id = new int[numStudents];

        for(int i = 0; i < numStudents; i++) {
            output.println("Select the student you want to move");
            output.println("[ insert his color and where you want to move it (HALL | ISLAND) ]");
            output.print("> ");

            String inputString = input.nextLine();

            String[] temp = inputString.split("\\s+");

            student[i] = temp[0];
            to[i] = temp[1];

            int islandId = 0;

            if(to[i].equals("ISLAND")){
                output.println("Select the island where you want to move the student \n[Insert the ID]");
                output.print("> ");

                id[i] = input.nextInt();

            }

            notifyObserver(viewObserver -> viewObserver.onUpdateStudent(from, student, to, id));
        }
    }


    @Override
    public void askIslandToMove() {
        output.println("Insert the id of the chosen Island: ");
        output.print("> ");

        int islandId = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(islandId));
    }

    @Override
    public void connectionLost() {

    }

    public void clearCli(){
    }

    public void printLine(){
        output.println("\n-----------------------------------------------------------");
    }

    @Override
    public void startGame() {
        clearCli();

        output.println("\nThe game is starting...\n\n");
        printLine();

        showBoard();
    }

    @Override
    public void showGenericMessage(String message) {
        output.println(message);
    }

    public String readLine(){
        String read = input.nextLine();


        if(read.contains("CHARACTER")){
            String[] temp = read.split("\\s+");

            int effectId = Integer.parseInt(temp[1]);
            notifyObserver(viewObserver -> viewObserver.onUpdateCharacter(effectId));

            return null;
        }

        else return read;
    }

    public void createModel(ReducedModel reducedModel){
        this.reducedModel = reducedModel;
    }

    public String[] sideMenu(){
        String sideMenu = "";

        sideMenu += "\n\n-----------------------------------" +
                "\n USERNAME " +
                "\n-----------------------------------\n" +
                reducedModel.getUsername() +
                "\n-----------------------------------\n" +
                "\n COLOR " +
                "\n-----------------------------------\n" +
                reducedModel.getColor() +
                "\n-----------------------------------\n" +
                "\n STATUS" +
                "\n-----------------------------------\n" +
                status;


        String[] sideMenuRows = sideMenu.split("\n");


        return sideMenuRows;
    }

    @Override
    public void askMotherNatureMove() {
        output.println("\nEnter the number of steps you want to move motherNature");
        output.print("> ");

        int steps = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateMotherNature(steps));
    }
}

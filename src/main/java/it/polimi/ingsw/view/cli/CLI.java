package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedCharacter;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.*;
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
    private String playerUsername;

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

            if(address.equals("")) serverInfo.add(defaultAddress);
            else serverInfo.add(address);
            isValid = true;

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
        input.reset();

    }

    @Override
    public void askUsername(){
        printLine();

        do {
            output.println("\nEnter username: ");
            output.print("> ");

            username = input.nextLine();
        }while(username.equals(""));

        notifyObserver(obs -> obs.onUpdateLoginMessage(username));
        input.reset();
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
        input.reset();
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
        input.reset();
    }


    @Override
    public void askTowerColor(List<TowerColor> availableColors) {
        String chosenTowerColor;
        boolean isValid;
        String bug = input.nextLine(); //??????

        do{

            output.println("\nYou have to choose a tower color " + availableColors );
            output.print("> ");


            chosenTowerColor = input.nextLine().toUpperCase(Locale.ROOT);

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
        input.reset();
    }

    @Override
    public void startTurn() {
    }

    @Override
    public void setBoard(ReducedBoard reducedBoard){
        this.reducedModel.setReducedBoard(reducedBoard);
    }

    public void showBoard() {
        output.println("\n\nUSERNAME: " +
                        reducedModel.getUsername() +
                        "\nCOLOR: " +
                        reducedModel.getColor() +
                        "\n\nPLAYER BOARD\n" +
                        reducedModel.getReducedBoard().printPlayerBoard());

        output.println("\n");
        String infos = "";
        String menu = "";

        if(reducedModel.isExpertMode()){
            infos +="\nType CHARACTER <CharacterId> to activate the effect " +
                    "\nType DESC <CharacterId> to see a desc of the character effect" +
                    "\n\nCHARACTERS: \n";

            for(ReducedCharacter character : reducedModel.getReducedCharacters()){
                infos +=
                        "\nID: " + character.getEffectId() +
                                "\nCOST: " + character.getCost() +"\n--------\n";

            }

            output.print(infos + "\n\n");
        }

        output.println(reducedModel.getReducedBoard().printIslands());
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
        input.reset();
    }

    @Override
    public void winMessage(String winner) {
        output.println(winner + " has won!!");
    }

    @Override
    public void askCardToPlay(List<AssistantCard> assistantCards, List<AssistantCard> cardsPlayed) {
        int cardId;

        do {
            output.println("\n\nThis is your deck -> " + printDeck(assistantCards));
            output.print("\nThese are the cards that have been played this turn -> ");
            for (AssistantCard assistantCard : cardsPlayed) {
                output.print(assistantCard.getValue() + "   ");
            }

            output.print("\n");
            output.println("Choose the card you want to play this turn! ");
            output.println("[ insert the number of the card ]");
            output.print("> ");


            cardId = Integer.parseInt(readLine());

        }while(cardId < 0 || cardId > 10);


        int finalCardId = cardId;

        notifyObserver(viewObserver -> viewObserver.onUpdateCard(finalCardId));
        input.reset();

        output.println("Wait... other player is playing his turn");
    }

    private String printDeck(List<AssistantCard> assistantCards) {
        String deck = "\n";
        int i = 0;

        for(AssistantCard assistantCard : assistantCards){
            deck += " Card  " + assistantCard.getValue() +
                    " [ V = " + assistantCard.getValue() + " ] " +
                    " [ M = " + assistantCard.getMaxMotherNatureMoves() + " ] \t";

            if(i == 4) deck += "\n";
            i++;
        }

        return deck;
    }

    @Override
    public void askCloud() {
        output.println(reducedModel.getReducedBoard().printClouds());
        boolean isValid = false;
        int cloud = 0;

        do {
            output.println("Choose the cloud you want to get the students from");
            output.println("[ insert the number of the cloud ]");
            output.print("> ");

            String read = readLine();

            if (read.equals(null)) {
                activatingCharacter = true;
            } else {
                cloud = Integer.parseInt(read);
            }

            if (cloud == 1 || cloud == 2 || cloud == 3) isValid = true;

        }while (!isValid) ;

            int finalCloud = cloud;

            notifyObserver(viewObserver -> viewObserver.onUpdateCloud(finalCloud - 1));
            input.reset();

    }


    @Override
    public void askStudentToMove() {
        output.flush();

        showBoard();

        String from;
        String student;
        String to;
        int id = 0;

        int i = 0;

        from = "ENTRANCE";

        output.println("Select the student you want to move");
        output.println("[ insert his color and where you want to move it (DINNER | ISLAND) ]");
        output.print("> ");
        input.reset();


        String inputString;

        do{
            inputString = readLine();
        }while(inputString.isEmpty());


        if(inputString.equals("")) inputString=input.nextLine();

        String[] temp = inputString.split("\\s+");

        student = temp[0].toUpperCase();
        to = temp[1].toUpperCase();

        int islandId = 0;

        if(to.equals("ISLAND")){
            output.println("Select the island where you want to move the student \n[Insert the ID]");
            output.print("> ");

            id = input.nextInt();
        }

        int finalId = id;

        reducedModel.moveStudent(from, student, to, id);

        notifyObserver(viewObserver -> viewObserver.onUpdateStudent(from, student, to, finalId));

        input.reset();
    }


    @Override
    public void askIslandToMove() {
        output.println("Insert the id of the chosen Island: ");
        output.print("> ");

        int islandId = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(islandId));
        input.reset();
    }

    @Override
    public void connectionLost() {

    }

    @Override
    public void startGame(String firstPlayer, ReducedModel reducedModel) {
        this.reducedModel = reducedModel;

        clearCli();

        output.println("\nThe game is starting...\n\n");
        printLine();

        showBoard();
    }

    public void clearCli(){
        output.flush();
    }

    public void printLine(){
        output.println("\n-----------------------------------------------------------");
    }

    @Override
    public void showGenericMessage(String message) {
        output.println(message);
    }

    public String readLine(){
        input.reset();

        String read;

        read = input.nextLine();

        if(read.equals("")) read = input.nextLine();

        if(read.contains("CHARACTER")){
            String[] temp = read.split("\\s+");

            int effectId = Integer.parseInt(temp[1]);
            notifyObserver(viewObserver -> viewObserver.onUpdateCharacter(effectId));
            input.reset();

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
                reducedModel.getPlayerUsername() +
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

        int steps = Integer.parseInt(readLine());

        moveMotherNature(steps);

        notifyObserver(viewObserver -> viewObserver.onUpdateMotherNature(steps));
        input.reset();
    }

    @Override
    public void setTurnInfo(int steps) {
        reducedModel.setMaxSteps(steps);
    }

    @Override
    public void activateCharacter(int id) {
        reducedModel.activateCharacter(id);
    }

    @Override
    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    @Override
    public void mergeIsland(int island1, int island2) {

    }

    @Override
    public void updateModel(HashMap<String, Integer> turnCardsPlayed) {

    }

    @Override
    public void changeProfOwner(String professorOwner, Student color) {

    }

    @Override
    public void conquerIsland(int island, String islandOwner) {

    }

    @Override
    public void updateMotherNature(int steps) {

    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateIslands(ArrayList<ReducedIsland> islands) {

    }

    private void moveMotherNature(int steps) {
        if(steps < reducedModel.getMaxSteps()){
            reducedModel.getReducedBoard().moveMotherNature(steps);
        }
    }

    @Override
    public void updateClouds(int cloudId) {

    }

    @Override
    public void fillClouds(Cloud[] clouds) {

    }

    @Override
    public void askStudentEffect(int effectId) {

    }

    @Override
    public void askIslandEffect(int effectId) {

    }

    @Override
    public void askSwitch() {

    }

    @Override
    public void updateCharacterStudents(ArrayList<Student> students, int id) {

    }

    @Override
    public void notifyCharacterActivation(int effectId, boolean activated) {

    }

    @Override
    public void askEffect12Students(Student color) {

    }
}

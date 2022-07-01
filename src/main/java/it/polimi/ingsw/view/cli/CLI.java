package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.PhaseType;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.exceptions.EmptyCloudException;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedBoard;
import it.polimi.ingsw.network.client.reducedModel.ReducedCharacter;
import it.polimi.ingsw.network.client.reducedModel.ReducedIsland;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CLI extends ViewObservable implements View {
    private final Scanner input = new Scanner(System.in);
    private final PrintStream output;
    private String username;
    public static final Logger LOGGER = Logger.getLogger(CLI.class.getName());
    private ReducedModel reducedModel;
    private PhaseType currentPhase;
    private boolean activatingCharacter;
    private final String INV_INP = "Invalid input! ";
    private String playerUsername;
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


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

            if(address.equals("") || address.equals("localhost")){
                serverInfo.add(defaultAddress);
                isValid = true;
            }
            else if(isValidIp(address)){
                serverInfo.add(address);
                isValid = true;
            }
            else{
                serverInfo.add(address);
                isValid = false;
            }


        }while(!isValid);

        do{
            output.print("Enter the server port [ Default = " + defaultPort + "] : ");
            String port = input.nextLine();

            if(port.equals("")){
                serverInfo.add(defaultPort);
                isValid = true;
            }
            else if(Integer.parseInt(port) >= 1 && Integer.parseInt(port) <= 65535){
                serverInfo.add(port);
                isValid = true;
            }
            else{
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
    public void setBoard(ReducedBoard reducedBoard){
        this.reducedModel.setReducedBoard(reducedBoard);
    }

    public void showBoard() {
        output.println("\n");
        String infos = "";
        String menu = "";

        output.println(reducedModel.getReducedBoard().printIslands());

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

        output.println("\n\nUSERNAME: " +
                reducedModel.getPlayerUsername() +
                "\nCOLOR: " +
                reducedModel.getColor() +
                "\n\nPLAYER BOARD\n" +
                reducedModel.getReducedBoard().printPlayerBoard());
    }

    @Override
    public void error(String error) {
        output.println("\n" + error);

        if(error.contains("coins")){
            resumePhase();
        }
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
        String id;
        int cardId = 0;
        List<Integer> idPlayed = new ArrayList<>();

        if(assistantCards.size() > 1 && reducedModel.getUsername().size() == 2 || assistantCards.size() > 2 && reducedModel.getUsername().size() == 3){
            for(AssistantCard card : cardsPlayed){
                idPlayed.add(card.getValue());
            }
        }

        currentPhase = PhaseType.CARD;

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
            
            id = readLine();

            while (id != null && id.equals("desc")){
                output.print("\n> ");
                input.reset();

                id = readLine();
            }

            if(id != null) cardId = Integer.parseInt(id);

            if(idPlayed.contains(cardId)){
                cardId = -1;
                output.println("Card already played! ");
            }

        }while((cardId < 0 || cardId > 10) || (id != null && id.equals("error")));


        int finalCardId = cardId;

        if(finalCardId != 0 ){
            notifyObserver(viewObserver -> viewObserver.onUpdateCard(finalCardId));
            currentPhase = PhaseType.NOT_MYTURN;
            output.println("Wait... other player is playing his turn");
        }else{
            reducedModel.setTurnCards(cardsPlayed);
            reducedModel.setDeck(assistantCards);
        }

        input.reset();


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
        currentPhase = PhaseType.CLOUD;

        output.println(reducedModel.getReducedBoard().printClouds());
        boolean isValid = false;
        int cloud = 0;

        do {
            output.println("Choose the cloud you want to get the students from");
            output.println("[ insert the number of the cloud ]");
            output.print("> ");

            String read = readLine();

            while(read != null && read.equals("desc")){
                output.print("\n> ");
                input.reset();

                read = readLine();
            }

            if (read == null) {
                activatingCharacter = true;
                cloud = -1;
            } else {
                cloud = Integer.parseInt(read);
            }

            if (cloud == 1 || cloud == 2 || cloud == 3 || cloud == -1) isValid = true;

        }while (!isValid) ;

            int finalCloud = cloud;

        if(finalCloud != -1){
            for(Student student : reducedModel.getReducedBoard().getClouds()[cloud - 1].getStudents()){
                reducedModel.getReducedBoard().getPlayerBoard().addEntranceStudent(String.valueOf(student));
            }

            notifyObserver(viewObserver -> viewObserver.onUpdateCloud(finalCloud - 1));
            input.reset();

            currentPhase = PhaseType.NOT_MYTURN;
        }
    }


    @Override
    public void askStudentToMove() {

        output.flush();

        input.reset();

        currentPhase = PhaseType.MOVE_STUDENT;

        showBoard();

        String from;
        String student;
        String to;
        int id = 0;

        int i = 0;

        from = "ENTRANCE";

        output.println("Select the student you want to move");
        output.println("[ insert his color and where you want to move it (<COLOR> <DINNER | ISLAND> ]");
        output.print("> ");
        input.reset();


        String inputString;

        inputString = readLine();

        while(inputString != null && inputString.equals("desc")){
            output.print("\n> ");
            input.reset();

            inputString = readLine();
        }

        if(inputString != null && inputString.equals("")) inputString = input.nextLine();


        if(inputString != null){
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

    }

    @Override
    public void startGame(String firstPlayer, ReducedModel reducedModel) {

        this.reducedModel = reducedModel;

        reducedModel.setUsername(username);

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
        else if(read.contains("DESC")){
            String[] temp = read.split("\\s+");
            int effectId;

            if(temp.length == 2){
                effectId = Integer.parseInt(temp[1]);
                output.println(reducedModel.getCharacterById(effectId).getCharacterDesc());
                return "desc";
            }
            else{
                output.println("Invalid input!");
                return "error";
            }
        }
        else return read;
    }

    public void createModel(ReducedModel reducedModel){
        this.reducedModel = reducedModel;
    }


    @Override
    public void askMotherNatureMove() {
        currentPhase = PhaseType.MOTHER_NATURE;
        int steps;

        output.println("\nEnter the number of steps you want to move motherNature");
        output.print("> ");

        String read = readLine();

        while(read != null && read.equals("desc")){
            output.print("\n> ");
            input.reset();

            read = readLine();
        }

        if (read == null || read.equals("error")) {
            activatingCharacter = true;
            steps = -1;
        } else {
            steps = Integer.parseInt(read);
        }

        if(steps <= reducedModel.getMaxSteps() && steps != -1){
            moveMotherNature(steps);
            int finalSteps = steps;

            notifyObserver(viewObserver -> viewObserver.onUpdateMotherNature(finalSteps));
            input.reset();
        }
        else{
            output.println("Invalid move! ");
            askMotherNatureMove();
        }
    }

    @Override
    public void setTurnInfo(int steps) {
        reducedModel.setMaxSteps(steps);
    }

    @Override
    public void activateCharacter(int id) {
    }

    @Override
    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    @Override
    public void mergeIsland(int island1, int island2) {
        reducedModel.getReducedBoard().mergeIslands(island1, island2);

        output.println("Island " + island1 + " and Island " + island2 + " have been merged");
    }

    @Override
    public void updateModel(HashMap<String, Integer> turnCardsPlayed) {
    }

    @Override
    public void changeProfOwner(String professorOwner, Student color) {
        reducedModel.setProfOwner(professorOwner, color);
    }

    @Override
    public void conquerIsland(int island, String islandOwner) {
        reducedModel.getReducedBoard().getIslands().get(island).setOwner(islandOwner);
        output.println(islandOwner + " is now the owner of Island " + island);
    }

    @Override
    public void updateMotherNature(int steps) {
        reducedModel.getReducedBoard().moveMotherNature(steps);
    }


    @Override
    public void updateIslands(ArrayList<ReducedIsland> islands) {
        reducedModel.getReducedBoard().setIslands(islands);
    }

    private void moveMotherNature(int steps) {
        if(steps < reducedModel.getMaxSteps()){
            reducedModel.getReducedBoard().moveMotherNature(steps);
        }
    }

    @Override
    public void updateClouds(int cloudId) {
        try {
            reducedModel.getReducedBoard().getClouds()[cloudId].getStudentsFromCloud();
        } catch (EmptyCloudException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillClouds(Cloud[] clouds) {
        reducedModel.getReducedBoard().setClouds(clouds);
    }

    @Override
    public void askStudentEffect(int effectId) {
        switch (effectId){
            case 1 -> {
                output.println("Select a student you want to move to an Island");

                output.println(reducedModel.getCharacterById(effectId).getStudents());

                output.print("> ");
                String selectedStudent = input.nextLine();

                notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(selectedStudent, effectId));
            }

            case 7 -> {
                ColorIntMap map = new ColorIntMap();
                HashMap<Student, Integer> m = map.getMap();
                String answer = null;
                int count = 0;
                String cardStud;
                String test;

                do{
                    input.reset();

                    boolean isValid;
                    ArrayList<Student> students = new ArrayList<>();

                    do{
                        output.println("Select a student from the card");

                        output.println(reducedModel.getCharacterById(effectId).getStudents());

                        output.print("> ");

                        cardStud = input.nextLine();

                        if(!reducedModel.getCharacterById(effectId).getStudents().contains(Student.valueOf(cardStud))){
                            output.println("The given student is not in the card, insert another student");
                            isValid = false;
                        }
                        else{
                            isValid = true;
                            students.add(Student.valueOf(cardStud));
                            reducedModel.getCharacterById(effectId).removeStudent(Student.valueOf(cardStud));
                        }
                    }while(!isValid);


                    do{
                        output.println("Select a student from the entrance");

                        output.print("> ");

                        String selectedStudent = input.nextLine();

                        int pos = m.get(Student.valueOf(selectedStudent));

                        if(reducedModel.getReducedBoard().getPlayerBoard().getEntranceStudents()[pos] < 0){
                            output.println("The given student is not in the entrance, insert another student");
                            isValid = false;
                        }
                        else{
                            isValid = true;
                            students.add(Student.valueOf(selectedStudent));
                            reducedModel.getReducedBoard().getPlayerBoard().addEntranceStudent(cardStud);
                            reducedModel.getReducedBoard().getPlayerBoard().removeEntranceStudent(selectedStudent);
                        }
                    }while(!isValid);


                    notifyObserver(viewObserver -> viewObserver.onUpdateSwitchStudents(students, 7));

                    count++;

                    if(count < 3){
                        do{
                            output.println("Do you want to switch other students? [y/n]");
                            output.print("> ");

                            answer = input.nextLine();

                            test = answer.toUpperCase();

                        }while(!test.equals("N") && !test.equals("Y"));
                    }

                }while(answer != null && answer.equals("Y") && count < 3);
            }
            case 11 -> {
                String selectedStudent;

                do{
                    output.println("Select a student from the card");

                    output.println(reducedModel.getCharacterById(effectId).getStudents());

                    output.print("> ");

                    selectedStudent = input.nextLine();

                }while(!reducedModel.getCharacterById(11).getStudents().contains(Student.valueOf(selectedStudent)));


                String finalSelectedStudent = selectedStudent;
                
                notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(finalSelectedStudent, effectId));
            }

            case 9,12 -> {
                String selectedColor;
                
                do{
                    input.reset();
                    
                    output.println("Insert a color");
                    output.print("> ");

                    selectedColor = input.nextLine();
                    selectedColor.toUpperCase();

                }while(!selectedColor.equals("RED") && !selectedColor.equals("YELLOW") && !selectedColor.equals("GREEN") && !selectedColor.equals("BLUE") && !selectedColor.toUpperCase().equals("PINK"));


                String finalSelectedColor = selectedColor;
                
                notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(finalSelectedColor, effectId));
            }
        }
    }

    @Override
    public void askIslandEffect(int effectId) {
        output.println("Insert the id of the island you want");

        output.print("> ");

        int id = input.nextInt();

        notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(id , effectId));

    }

    @Override
    public void askSwitch() {
        ArrayList<Student> students = new ArrayList<>();
        String answer = null;
        boolean isValid;
        ColorIntMap map = new ColorIntMap();
        HashMap<Student, Integer> m = map.getMap();
        int effectId = 10;
        String entranceStud;
        int count = 0;
        String test;

        do{
            do{
                input.reset();

                output.println("Select a student from the entrance");

                output.print("> ");

                entranceStud = input.nextLine();

                int pos = m.get(Student.valueOf(entranceStud));

                if(reducedModel.getReducedBoard().getPlayerBoard().getEntranceStudents()[pos] <= 0){
                    output.println("The given student is not in the entrance, insert another student");
                    isValid = false;
                }
                else{
                    isValid = true;
                    students.add(Student.valueOf(entranceStud));
                    reducedModel.getReducedBoard().getPlayerBoard().removeEntranceStudent(entranceStud);
                }
            }while(!isValid);

            do{
                input.reset();

                output.println("Insert the color of the student you want to get from the dinner");

                output.print("> ");

                String selectedStudent = input.nextLine();

                int pos = m.get(Student.valueOf(selectedStudent));

                if(reducedModel.getReducedBoard().getPlayerBoard().getHallStudents()[pos] <= 0){
                    output.println("The given student is not in the dinner, insert another student");
                    isValid = false;
                }
                else{
                    isValid = true;
                    students.add(Student.valueOf(selectedStudent));
                    reducedModel.getReducedBoard().getPlayerBoard().addHallStudent(entranceStud);
                    reducedModel.getReducedBoard().getPlayerBoard().addEntranceStudent(selectedStudent);

                    reducedModel.getReducedBoard().getPlayerBoard().removeEntranceStudent(selectedStudent);
                }
            }while(!isValid);

            count ++;

            if(count < 2){
                do{
                    output.println("Do you want to switch other students? [y/n]");
                    output.print("> ");

                    answer = input.nextLine();

                    test = answer.toUpperCase();

                }while(!test.equals("N") && !test.equals("Y"));
            }

        }while(answer != null && answer.equals("Y") && count < 2);


        notifyObserver(viewObserver -> viewObserver.onUpdateSwitchStudents(students, 10));
    }

    @Override
    public void updateCharacterStudents(ArrayList<Student> students, int id) {
        reducedModel.getCharacterById(id).setStudents(students);
    }

    @Override
    public void notifyCharacterActivation(int effectId, boolean activated, String owner) {
        if(activated){
            output.println(owner + " has activated character " + effectId);
            reducedModel.activateCharacter(effectId, owner);

            if (effectId == 4) {
                reducedModel.setMaxSteps(reducedModel.getMaxSteps() + 2);
            }

            resumePhase();
        }else {
            if(effectId != 5) output.println("Character " + effectId + " is not active anymore");
        }
    }

    private void resumePhase() {
        if(currentPhase != null) {
            switch (currentPhase) {

                case CARD -> askCardToPlay(reducedModel.getDeck(), reducedModel.getTurnCards());

                case MOTHER_NATURE -> askMotherNatureMove();

                case CLOUD -> askCloud();

                case MOVE_STUDENT -> askStudentToMove();

                case NOT_MYTURN -> output.println("Wait...");
            }
        }
    }

    @Override
    public void askEffect12Students(Student color) {
        ColorIntMap map = new ColorIntMap();
        HashMap<Student, Integer> posMap = map.getMap();

        int numStudents = reducedModel.getReducedBoard().getPlayerBoard().getEntranceStudents()[posMap.get(color)];

        if(numStudents >= 3){
            for(int i = 0; i < 3; i++){
                reducedModel.getReducedBoard().getPlayerBoard().removeHallStudent(String.valueOf(color));
            }
        }
        else{
            for(int i = 0; i < numStudents; i++){
                reducedModel.getReducedBoard().getPlayerBoard().removeHallStudent(String.valueOf(color));
            }
        }

        output.println(numStudents + " students have been taken from the dinner! ");

        resumePhase();
    }

    @Override
    public void setMotherNature(int motherNature) {
        reducedModel.getReducedBoard().setMotherNature(motherNature);
    }

    @Override
    public void showDisconnection(String username) {
        output.println("The game has ended because " + username + " disconnected");

        System.exit(0);
    }

    @Override
    public void backToChoice() {
    }

    public static boolean isValidIp(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    @Override
    public void noEntryTile(int islandId) {
        reducedModel.getReducedBoard().getIslands().get(islandId).setNoEntryTile(false);
    }
}

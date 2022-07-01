package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.network.client.reducedModel.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.*;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.GameHandler;
import it.polimi.ingsw.observer.Observer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Controller class
 */
public class GameController implements Observer {

    private int NUM_STUDENTS;
    private final Game model;
    private int movenStudents;
    private ArrayList<String> activePlayers;
    private TurnController turnController;
    private GameHandler gameHandler;
    private int phase;
    private int playerTurn;
    private HashMap<String, Integer> playerTurnCards;


    /**
     * Default constructor
     * @param model
     */

    public GameController(Game model, GameHandler gameHandler) {
        if(model.getPlayers().size() == 2) NUM_STUDENTS = 3;
        else NUM_STUDENTS = 4;

        this.model = model;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
        this.activePlayers = new ArrayList<>(model.getUsernames());
        phase = 0;
        playerTurnCards = new HashMap<>();
        playerTurn = 0;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    /**
     * method used to activate a character after receiving the chosenIsland
     *
     * @param id        the id of the character
     * @param chosenIsland      the island the player choose for applying the effect
     */
    public void activateIslandCharacter(int id, Island chosenIsland) {
        Player player = turnController.getCurrentPlayer();

        Character character = model.getCharacter(id);

        character.setOwner(player);

        try {
            if(id == 5 && ((Effect5)character.getEffect()).getNumEntryTiles() == 0){
                gameHandler.sendMessage(new ErrorMessage("No more entry tiles! ", ErrorType.GENERIC), player.getUsername());
            }else{
                character.applyEffect(chosenIsland);
            }

        } catch (EmptyBagException e) {
            endGame();
        }

        buyCharacter(player, character);


        if(id != 5) model.getActivatedCharacters().add(character.getEffectId());

        if(id == 1){
            ArrayList<Student> students = ((Effect1)character.getEffect()).getStudents();
            if(gameHandler != null) gameHandler.sendMessageToAll(new UpdateCharacterStudents(students, id));
        }

        updateIslands(character.getOwner().getUsername());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(id, true, character.getOwner().getUsername()));
    }

    /**
     * method used to activate a character after receiving the chosen Student
     *
     * @param id        the id of the character
     * @param chosenStudent      the student the player choose to move
     */
    public void activateStudentCharacter(int id, Student chosenStudent){
        Player player = turnController.getCurrentPlayer();

        Character character = model.getCharacter(id);

        character.setOwner(player);
        try {
            character.applyEffect(chosenStudent);
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }

        buyCharacter(player, character);

        model.getActivatedCharacters().add(character.getEffectId());

        if(id == 1){
            ArrayList<Student> students = ((Effect1)character.getEffect()).getStudents();
            if(gameHandler != null) gameHandler.sendMessageToAll(new UpdateCharacterStudents(students, id));
        }

        if(id == 11){
            ArrayList<Student> students = ((Effect11)character.getEffect()).getStudents();
            if(gameHandler != null) gameHandler.sendMessageToAll(new UpdateCharacterStudents(students, id));
        }

        updateIslands(character.getOwner().getUsername());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(id, true, character.getOwner().getUsername()));
    }

    /**
     * Adds the coins to the general supply and increases the character cost
     * @param player the player who activated the card
     * @param character the activated character
     */
    private void buyCharacter(Player player, Character character) {
        player.setNumCoins(player.getNumCoins() - character.getCost());
        model.addCoins(character.getCost());
        character.setCost(character.getCost()+1);
    }

    /**
     * method used to activate the character that doesn't need parameters
     *
     * @param id        the id of the character
     */
    public void activateCharacter(int id){
        Player player = turnController.getCurrentPlayer();

        Character character = model.getCharacter(id);

        character.setOwner(player);
        character.applyEffect();

        buyCharacter(player, character);

        model.getActivatedCharacters().add(character.getEffectId());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(id, true, character.getOwner().getUsername()));
    }

    /**
     * Method used to move a student
     * @param from      the movable object where the student is
     * @param color     the color of the student the player wants to move
     * @param to        the movable object where the player wants to move the student
     */
    public void moveStudent(Movable from, Student color, Movable to, boolean profCheck) throws InvalidMoveException {
        to.addStudent(from.removeStudent(color));

        if(profCheck) professorCheckController(color);
    }

    private void professorCheckController(Student color) {
        if(model.professorCheck(color)){
            int colorPos = model.createColorIntMap(color);
            String professorOwner = model.getBoard().getProfessors()[colorPos].getOwner().getUsername();

            if(gameHandler != null) gameHandler.sendMessageToAll(new UpdateModelMessage(professorOwner, color));

            if(gameHandler != null) gameHandler.sendMessage(new GenericMessage("You are now the owner of the " + color + " professor", GenericType.PROFESSOR), professorOwner);
            if(gameHandler != null) gameHandler.sendMessageToAllExcept(new GenericMessage(professorOwner + " is now the owner of the " + color + " professor", GenericType.PROFESSOR), professorOwner);
        }
    }

    /**
     * Method called when a user plays a card
     * @param cardPlayed        the card the player decided to play
     * @throws CardAlreadyPlayedException       the player can't play assistant card already played in this round
     */
    public void playCard(AssistantCard cardPlayed) throws CardAlreadyPlayedException {
        Player player = turnController.getCurrentPlayer();

        if(!turnController.getTurnCardsPlayed().contains(cardPlayed))
            player.playCard(cardPlayed);

        if(turnController != null) {
            if (turnController.getTurnCardsPlayed().contains(cardPlayed)) throw new CardAlreadyPlayedException();
            else {
                player.playCard(cardPlayed);
                turnController.getTurnCardsPlayed().add(cardPlayed);
            }
        }

        if(turnController.getCurrentPlayer().getDeck().size() == 0) endGame();
    }


    public Game getGame() {
        return model;
    }

    /**
     * Method called when the player wants to move mother nature
     * @param steps number of steps he wants to move mother nature
     * @throws InvalidMotherNatureMovesException
     */
    public void moveMotherNature(int steps) throws InvalidMotherNatureMovesException {
        Player player = turnController.getCurrentPlayer();

        if(model.getActivatedCharacters().contains(4)){
            if(steps - 2 > player.getMotherNatureMoves() ) throw new InvalidMotherNatureMovesException();
            else{
                model.getBoard().moveMotherNature(steps);
            }
        }
        else{
            if(steps > player.getMotherNatureMoves()) throw new InvalidMotherNatureMovesException();
            else{
                model.getBoard().moveMotherNature(steps);
            }
        }

        if(model.checkAfterMotherNature()){
            String islandOwner = null;

            if(model.getBoard().getMotherNatureIsland().getOwner() != null){
                islandOwner = model.getBoard().getMotherNatureIsland().getOwner().getUsername();

            }

            if(islandOwner!=null && model.getPlayerByUsername(islandOwner).getNumTowers() == 0){
                endGame();
            }

            if(gameHandler != null){
                gameHandler.sendMessageToAll(new UpdateModelMessage(model.getBoard().getMotherNature(), String.valueOf(model.getPlayerByUsername(islandOwner).getTowerColor())));
                gameHandler.sendMessage(new GenericMessage("You are now the owner of the " + model.getBoard().getMotherNature() + " island", GenericType.ISLAND_OWNER), islandOwner);
                gameHandler.sendMessageToAllExcept(new GenericMessage(islandOwner + " is now the owner of the " + model.getBoard().getMotherNature() + " island", GenericType.ISLAND_OWNER), islandOwner);
            }

            model.mergeCheck();
        }
    }

    /**
     * Handles the end of the game and calculates the winner
     */
    private void endGame() {
        Integer[] numIslands = new Integer[model.getNumPlayers()];

        for(int i = 0; i < model.getBoard().getIslands().size(); i++){
            if(model.getBoard().getIslands().get(i).getOwner() != null){
                int index = model.getPlayers().indexOf(model.getBoard().getIslands().get(i).getOwner());

                numIslands[index]++;
            }
        }

        List<Integer> num = Arrays.asList(numIslands);

        Integer max = num
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        int playerIndex = Arrays.asList(numIslands).indexOf(max);

        String winner = model.getPlayers().get(playerIndex).getUsername();

        gameHandler.sendMessageToAll(new WinMessage(winner));

        gameHandler.sendMessage(new GenericMessage("Congratulations! ", GenericType.WIN), winner);

        gameHandler.endGame();
    }

    /**
     * Removes the student from a cloud and puts it on the entrance
     * @param cloudId
     */
    public void moveStudentsFromCloud(int cloudId) {
        ArrayList<Student> students = new ArrayList<>();

        Cloud[] clouds = model.getBoard().getClouds();
        try {
            students = clouds[cloudId].getStudentsFromCloud();
        } catch (EmptyCloudException e) {
            e.printStackTrace();
        }

        turnController.getCurrentPlayer().getPlayerBoard().fillEntrance(students);
    }

    /**
     * Handles the received message
     * @param message the message received
     */
    public void messageReceived (Message message){
        switch (message.getMessageType()) {
            case CLOUD -> {
                ChooseCloudMessage chooseCloudMessage = (ChooseCloudMessage) message;

                moveStudentsFromCloud (chooseCloudMessage.getCloudId());

                gameHandler.sendMessageToAllExcept(new UpdateClouds(chooseCloudMessage.getCloudId()), message.getUsername());

                playerTurn++;

                if(turnController.nextPlayer()){
                    movenStudents = 0;
                    gameHandler.sendMessage(new AskStudent(), turnController.getCurrentPlayerUsername());
                }else{
                    playerTurn = 0;
                    phase = 0;
                    turnController.newTurn();
                }
            }

            case MOVE_MOTHERNATURE -> {
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) message;

                try {
                    moveMotherNature(moveMotherNatureMessage.getSteps());
                    updateMotherNature(message.getUsername());
                } catch (InvalidMotherNatureMovesException e) {
                    gameHandler.sendMessage(new ErrorMessage("Invalid mother nature move", ErrorType.INVALID_MOVE), turnController.getCurrentPlayerUsername());
                    gameHandler.sendMessage(new AskMotherNature(), turnController.getCurrentPlayerUsername());
                }

                gameHandler.sendMessage(new AskCloud(model.getBoard().getClouds()), turnController.getCurrentPlayerUsername());
            }

            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                parseParametersStudent(studentMessage);
            }

            case PLAY_CARD -> {
                PlayCardMessage msg = (PlayCardMessage) message;
                AssistantCard assistantCard = model.getPlayerByUsername(msg.getUsername()).getAssistantCardById(msg.getAssistantCard());

                try {
                    playCard(assistantCard);
                    playerTurnCards.put(msg.getUsername(), assistantCard.getValue());
                }catch (CardAlreadyPlayedException e){
                    gameHandler.sendMessage(new ErrorMessage(e.getMessage(), ErrorType.DUPLICATE_CARD), message.getUsername());
                    gameHandler.sendMessage(new AskCard(turnController.getCurrentPlayer().getDeck(), turnController.getTurnCardsPlayed()), turnController.getCurrentPlayerUsername());
                }

                gameHandler.sendMessageToAllExcept(new UpdateModelMessage(playerTurnCards), msg.getUsername());

                if(turnController.nextPlayer()){
                    gameHandler.sendMessage(new AskCard(turnController.getCurrentPlayer().getDeck(), turnController.getTurnCardsPlayed()), turnController.getCurrentPlayerUsername());
                }
                else{
                    playerTurn = 0;
                    movenStudents = 0;

                    turnController.actionPhase();
                }
            }

            case ACTIVATE_CHARACTER -> {
                ActivateCharacterMessage activateCharacterMessage = (ActivateCharacterMessage) message;

                int id = activateCharacterMessage.getEffectId();

                try{
                    activateGenericCharacter(id, message.getUsername());
                } catch (NotEnoughCoinException e){
                    gameHandler.sendMessage(new ErrorMessage("You don't have enough coins! ", ErrorType.NOT_ENOUGH_COINS), activateCharacterMessage.getUsername());
                }
            }

            case STUDENT_EFFECT -> {
                StudentEffectMessage msg = (StudentEffectMessage) message;

                if(msg.getEffectId() == 1){
                    ((Effect1)model.getCharacter(msg.getEffectId()).getEffect()).apply(model, msg.getChosenStudent());
                    gameHandler.sendMessage(new AskIsland(msg.getEffectId()), msg.getUsername());
                }
                else if(msg.getEffectId() == 12) {
                    gameHandler.sendMessageToAll(new Effect12Message(msg.getChosenStudent()));
                }
                else {
                    activateStudentCharacter(msg.getEffectId(), msg.getChosenStudent());
                }
            }

            case ISLAND_EFFECT -> {
                IslandEffectMessage msg = (IslandEffectMessage) message;

                activateIslandCharacter(msg.getEffectId(), model.getBoard().getIslands().get(msg.getChosenIslandId()));
            }

            case SWITCH_STUDENTS -> {
                String username = message.getUsername();

                SwitchStudents msg = (SwitchStudents) message;
                switch (msg.getEffectId()){
                    case 10 -> {
                        try {
                            activateEffect10(msg.getStudents(), username);
                        } catch (InvalidMoveException e) {
                            gameHandler.sendMessage(new ErrorMessage("Invalid move", ErrorType.INVALID_SWITCH), username);
                        }
                    }

                    case 7 -> {
                        activateEffect7(msg.getStudents(), msg.getUsername());
                    }
                }

            }
        }
    }

    /**
     * Notifies all the clients the update of mother nature
     * @param username
     */
    private void updateMotherNature(String username) {
        if(gameHandler != null) gameHandler.sendMessageToAllExcept(new UpdateMotherNature(model.getBoard().getMotherNature()), username);
    }

    /**
     * Activates the character n. 7
     * @param students the students chosen
     * @param username the username of the player who activated the card
     */
    private void activateEffect7(ArrayList<Student> students, String username){
        Player player = model.getPlayerByUsername(username);
        Character character = model.getCharacter(7);

        for(int i = 0; i < (students.size()); i = i + 2){
            ((Effect7)character.getEffect()).removeStudent(students.get(i));
            player.getPlayerBoard().addStudent(students.get(i));
            try {
                player.getPlayerBoard().removeStudent(students.get(i + 1));
            } catch (InvalidMoveException e) {
                gameHandler.sendMessage(new ErrorMessage("Invalid students selected", ErrorType.GENERIC), username);
            }
        }

        buyCharacter(player, character);

        model.getActivatedCharacters().add(character.getEffectId());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(7, true, username));
    }

    /**
     * Activates the character n.10
     * @param students the students chosen for the effect
     * @param username the username of the player who activated the card
     * @throws InvalidMoveException when the students given can't be moved
     */
    private void activateEffect10(ArrayList<Student> students, String username) throws InvalidMoveException {
        Player player = model.getPlayerByUsername(username);

        Character character = model.getCharacter(10);

        character.setOwner(player);

        player.getPlayerBoard().getDinnerRoom()[model.createColorIntMap(students.get(1))].removeStudent(students.get(1));
        player.getPlayerBoard().getDinnerRoom()[model.createColorIntMap(students.get(3))].removeStudent(students.get(3));

        player.getPlayerBoard().addStudent(students.get(0));
        professorCheckController(students.get(0));
        player.getPlayerBoard().addStudent(students.get(2));
        professorCheckController(students.get(2));

        buyCharacter(player, character);

        model.getActivatedCharacters().add(character.getEffectId());


        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(10, true, username));
    }

    /**
     * Handles the activation of a character and sends a message asking for student or island when needed
     * @param id the id of the effect
     * @param username the username of the player who asked for the character activation
     * @throws NotEnoughCoinException
     */
    private void activateGenericCharacter(int id, String username) throws NotEnoughCoinException {
        if(model.getCharacter(id).getCost() > model.getPlayerByUsername(username).getNumCoins()) throw new NotEnoughCoinException();

        switch (id) {
            case 1 -> gameHandler.sendMessage(new StudentsMessage(((Effect1) model.getCharacter(id).getEffect()).getStudents(), id), username);

            case 2,4,6,8 -> {
                activateCharacter(id);
            }

            case 3,5 -> {
                gameHandler.sendMessage(new AskIsland(id), username);
            }
            case 7 -> {
                gameHandler.sendMessage(new StudentsMessage(((Effect7) model.getCharacter(id).getEffect()).getStudents(), id), username);
            }

            case 9,12 -> {
                gameHandler.sendMessage(new StudentsMessage(null, id), username);
            }

            case 10 -> {
                gameHandler.sendMessage(new AskSwitchStudent(), username);
            }

            case 11 -> {
                gameHandler.sendMessage(new StudentsMessage(((Effect11) model.getCharacter(id).getEffect()).getStudents(), id), username);
            }
        }
    }

    /**
     * Parses the message parameters in the attributes needed to move a student
     * @param studentMessage the message received containing from, to and the student
     */
    private void parseParametersStudent(MoveStudentMessage studentMessage) {
        Movable from = null;
        Student student = null;
        Movable to = null;
        boolean checkProf = false;


            switch (studentMessage.getFrom()){
                case "ENTRANCE" -> {
                    from = model.getPlayerByUsername(studentMessage.getUsername()).getPlayerBoard();
                }
            }

            student = Student.valueOf(studentMessage.getColor());

            switch (studentMessage.getTo()){
                case "DINNER" -> {
                    ColorIntMap cMap = new ColorIntMap();
                    HashMap<Student, Integer> map = cMap.getMap();

                    to = model.getPlayerByUsername(studentMessage.getUsername()).getPlayerBoard().getDinnerRoom()[map.get(student)];

                    checkProf = true;
                }

                case "ISLAND" -> {
                    to = model.getBoard().getIslands().get(studentMessage.getId());
                }
            }

            try {
                moveStudent(from, student, to, checkProf);

                movenStudents++;

                if(movenStudents < NUM_STUDENTS){
                    gameHandler.sendMessage(new AskStudent(), turnController.getCurrentPlayerUsername());
                }
                else {
                    updateIslands(studentMessage.getUsername());

                    gameHandler.sendMessage(new AskMotherNature(), turnController.getCurrentPlayerUsername());
                }

            } catch (InvalidMoveException e) {
                String fromString = studentMessage.getFrom();
                String toString = studentMessage.getTo();
                String studentString = studentMessage.getColor();

                gameHandler.sendMessage(new ErrorMessage("Invalid move! You can't move " + studentString + " from " +
                        fromString + " to " + toString, ErrorType.INVALID_MOVE ), turnController.getCurrentPlayerUsername());

                gameHandler.sendMessage(new AskStudent(), turnController.getCurrentPlayerUsername());
            }

    }

    /**
     * Adds a player to the game
     * @param username the username of the player
     */
    public void addPlayer(String username){
        model.addPlayer(username);
        activePlayers.add(username);
    }

    @Override
    public void update(Message message) {
        if(message.getMessageType() == MessageType.NO_ENTRY_TILE){
            gameHandler.sendMessageToAll(message);
        }

        switch (message.getMessageType()){
            case UPDATE_MODEL -> {
                UpdateModelMessage modelMessage = (UpdateModelMessage) message;

                switch (modelMessage.getUpdateType()){
                    case MERGE -> {
                        updateMotherNature(message.getUsername());
                        gameHandler.sendMessageToAll(message);
                    }
                    case CONQUER -> {
                        gameHandler.sendMessageToAll(message);
                    }
                }
            }
        }
    }

    /**
     * Method called when a game start, it handles the setup phase and planning phase of a game, also creates the reduced version of the model
     */
    public void startGame() {
        ReducedModel reducedModel;
        
        turnController.firstPlayer();
        turnController.firstUsernameQueue();

        if(model.isExpertMode()){
            ReducedCharacter[] reducedCharacters = new ReducedCharacter[3];
            int i = 0;
            List<String> characterDesc = createCharacterDesc();

            for(Character character : model.getCharacters()){

                reducedCharacters[i] = new ReducedCharacter(character.getCost(), character.getEffectId(), characterDesc.get(character.getEffectId() - 1));
                if(character.getEffectId() == 1){
                    reducedCharacters[i].setStudents(((Effect1)character.getEffect()).getStudents());
                }
                if(character.getEffectId() == 7){
                    reducedCharacters[i].setStudents(((Effect7)character.getEffect()).getStudents());
                }
                if(character.getEffectId() == 11){
                    reducedCharacters[i].setStudents(((Effect11)character.getEffect()).getStudents());
                }

                i++;
            }


            for(Player player : model.getPlayers()){
                ReducedBoard reducedBoard = createBoard(player);
                reducedModel = new ReducedModel(model.getUsernames(), player.getTowerColor(), reducedCharacters,reducedBoard, model.isExpertMode());
                gameHandler.sendMessage(new StartGame(turnController.getCurrentPlayerUsername(), reducedModel), player.getUsername());
            }
        }
        else{
            for(Player player : model.getPlayers()){
                ReducedBoard reducedBoard = createBoard(player);
                reducedModel = new ReducedModel(model.getUsernames(), player.getTowerColor(), reducedBoard);
                gameHandler.sendMessage(new StartGame(turnController.getCurrentPlayerUsername(), reducedModel), player.getUsername());
            }
        }

        gameHandler.sendMessage(new GenericMessage("\n You are the first player! ", GenericType.GENERIC), turnController.getCurrentPlayerUsername());
        gameHandler.sendMessageToAllExcept(new GenericMessage("\n Wait... " + turnController.getCurrentPlayerUsername() + " is playing his turn! ", GenericType.GENERIC), turnController.getCurrentPlayerUsername());

        gameHandler.sendMessage(new AskCard(turnController.getCurrentPlayer().getDeck(), turnController.getTurnCardsPlayed()), turnController.getCurrentPlayerUsername());

    }

    /**
     * Creates the character desc by reading json file containing them
     * @return
     */
    private List<String> createCharacterDesc() {
        Gson gson = new Gson();
        List<String> desc = new ArrayList<>();

        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/characters.json"));

        ReducedCharacter[] reducedCharacters = gson.fromJson(reader, ReducedCharacter[].class);

        for (ReducedCharacter reducedCharacter : reducedCharacters) {
            desc.add(reducedCharacter.getCharacterDesc());
        }

        return desc;
    }

    /**
     * Creates a reduced version of the board for the player
     * @param player
     * @return the reduced version of the game board created
     */
    private ReducedBoard createBoard(Player player) {

        String[] owner = new String[12];

        int[] entrance = new int[5];
        int[] hall = new int[5];

        ArrayList<Student> students;
        ArrayList<ReducedIsland> islands = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            students = model.getBoard().getIslands().get(i).getStudents();
            if (model.getBoard().getIslands().get(i).getOwner() != null) {
                owner[i] = model.getBoard().getIslands().get(i).getOwner().getUsername();
            } else owner[i] = "No owner";

            islands.add(new ReducedIsland(students, owner[i], i, false));
        }

        for (int i = 0; i < 5; i++) {
            entrance = player.getPlayerBoard().getEntranceStudents();
            hall[i] = player.getPlayerBoard().getDinnerRoom()[i].getNumStudents();
        }

        ReducedPlayerBoard reducedPlayerBoard = new ReducedPlayerBoard(entrance, hall, player.getNumTowers());

        return new ReducedBoard(model.getBoard().getClouds(), owner, reducedPlayerBoard, model.getBoard().getMotherNature(), islands);
    }

    /**
     * Checks if the bag is not empty, if it is the game ends
     */
    public void checkBag() {
        if(model.getBoard().getBag().isEmpty()) endGame();
    }

    /**
     * Updates all the islands after a change and sends the island updated
     * @param username the username of the player that changed the state of the board
     */
    public void updateIslands(String username){
        String[] owner = new String[12];

        ArrayList<Student> students;
        ArrayList<ReducedIsland> islands = new ArrayList<>();

        for (int i = 0; i < model.getBoard().getIslands().size(); i++) {
            students = model.getBoard().getIslands().get(i).getStudents();


            if (model.getBoard().getIslands().get(i).getOwner() != null) {
                owner[i] = model.getBoard().getIslands().get(i).getOwner().getUsername();
            } else owner[i] = "No owner";

            if(!model.getBoard().getIslands().get(i).isNoEntryTile()) islands.add(new ReducedIsland(students, owner[i], i, false));
            else{
                islands.add(new ReducedIsland(students, owner[i], i, true));
            }

            if(i == model.getBoard().getMotherNature()) islands.get(i).setMotherNature(true);
            else {
                islands.get(i).setMotherNature(false);
            }
        }

        if(gameHandler != null && username == null) gameHandler.sendMessageToAll(new UpdateModelMessage(islands));
        else if(gameHandler != null) gameHandler.sendMessageToAllExcept(new UpdateModelMessage(islands), username);


    }
}
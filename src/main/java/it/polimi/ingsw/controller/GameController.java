package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.Effect1;
import it.polimi.ingsw.model.characters.Effect11;
import it.polimi.ingsw.model.characters.Effect7;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.network.client.reducedModel.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientmsg.*;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.GameHandler;
import it.polimi.ingsw.observer.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Controller class
 */
public class GameController implements Observer {

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
                character.applyEffect(chosenIsland);
            } catch (EmptyBagException e) {
                endGame();
            }

            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost() - 1);
            character.setCost(character.getCost() + 1);
            model.getActivatedCharacters().add(character.getEffectId());

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
        player.setNumCoins(player.getNumCoins() - character.getCost());
        model.removeCoins(character.getCost()-1);
        character.setCost(character.getCost()+1);
        model.getActivatedCharacters().add(character.getEffectId());

        if(id == 1){
            ArrayList<Student> students = ((Effect1)character.getEffect()).getStudents();
            if(gameHandler != null) gameHandler.sendMessageToAll(new UpdateCharacterStudents(students, id));
        }

        updateIslands(character.getOwner().getUsername());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(id, true, character.getOwner().getUsername()));
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
        player.setNumCoins(player.getNumCoins() - character.getCost());
        model.removeCoins(character.getCost()-1);
        character.setCost(character.getCost()+1);
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

            //TODO fix
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

            Island test = model.getBoard().getMotherNatureIsland();

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

    private void updateMotherNature(String username) {
        if(gameHandler != null) gameHandler.sendMessageToAllExcept(new UpdateMotherNature(model.getBoard().getMotherNature()), username);
    }

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


        player.setNumCoins(player.getNumCoins() - character.getCost());
        model.removeCoins(character.getCost() - 1);
        character.setCost(character.getCost()+1);
        model.getActivatedCharacters().add(character.getEffectId());

        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(7, true, username));
    }

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

        player.setNumCoins(player.getNumCoins() - character.getCost());
        model.removeCoins(character.getCost() - 1);
        character.setCost(character.getCost()+1);
        model.getActivatedCharacters().add(character.getEffectId());


        if(gameHandler != null) gameHandler.sendMessageToAll(new CharacterActivated(10, true, username));
    }

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

                if(movenStudents < 3){
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

    public void addPlayer(String username){
        model.addPlayer(username);
        activePlayers.add(username);


    }

    @Override
    public void update(Message message) {
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

    public void startGame() {
        ReducedModel reducedModel;
        
        turnController.firstPlayer();
        turnController.firstUsernameQueue();

        if(model.isExpertMode()){
            ReducedCharacter[] reducedCharacters = new ReducedCharacter[3];
            int i = 0;

            for(Character character : model.getCharacters()){
                reducedCharacters[i] = new ReducedCharacter(character.getCost(), character.getEffectId(), character.getDesc());
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

    public void checkBag() {
        if(model.getBoard().getBag().isEmpty()) endGame();
    }

    public void updateIslands(String username){
        String[] owner = new String[12];

        ArrayList<Student> students;
        ArrayList<ReducedIsland> islands = new ArrayList<>();

        for (int i = 0; i < model.getBoard().getIslands().size(); i++) {
            students = model.getBoard().getIslands().get(i).getStudents();
            if (model.getBoard().getIslands().get(i).getOwner() != null) {
                owner[i] = model.getBoard().getIslands().get(i).getOwner().getUsername();
            } else owner[i] = "No owner";
            islands.add(new ReducedIsland(students, owner[i], i, false));
        }

        if(gameHandler != null && username == null) gameHandler.sendMessageToAll(new UpdateModelMessage(islands));
        else if(gameHandler != null) gameHandler.sendMessageToAllExcept(new UpdateModelMessage(islands), username);


    }
}
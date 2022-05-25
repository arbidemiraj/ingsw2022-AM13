package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedCharacter;
import it.polimi.ingsw.network.client.reducedModel.ReducedPlayerBoard;
import it.polimi.ingsw.network.message.ErrorType;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.clientmsg.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.servermsg.*;
import it.polimi.ingsw.network.server.GameHandler;
import it.polimi.ingsw.observer.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Controller class
 */
public class GameController implements Serializable, Observer {

    @Serial
    private static final long serialVersionUID = 743913880093540550L;

    private final Game model;
    private String currentPlayer;
    private final ArrayList<String> activePlayers;
    private ArrayList<AssistantCard> turnCardsPlayed;
    private TurnController turnController;
    private GameHandler gameHandler;
    private int phase;
    private int playerTurn;
    private int turn = 0;


    /**
     * Default constructor
     * @param model
     */

    public GameController(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
        this.activePlayers = new ArrayList<>(model.getUsernames());
        turnCardsPlayed = new ArrayList<>();
        phase = 0;
        playerTurn = 0;
    }

    public static void removePlayer(String username) {
    }

    /**
     * method used to activate a character after receiving the chosenIsland
     *
     * @param id        the id of the character
     * @param chosenIsland      the island the player choose for applying the effect
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateIslandCharacter(int id, Island chosenIsland) throws NotEnoughCoinException {
        Player player = getCurrentPlayer();

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenIsland);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    /**
     * method used to activate a character after receiving the chosen Student
     *
     * @param id        the id of the character
     * @param chosenStudent      the student the player choose to move
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateStudentCharacter(int id, Student chosenStudent) throws NotEnoughCoinException {
        Player player = getCurrentPlayer();

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect(chosenStudent);
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }

    }

    /**
     * method used to activate the character that don't need parameters
     *
     * @param id        the id of the character
     * @throws NotEnoughCoinException       when the player hasn't enough coins to activate the character
     */
    public void activateCharacter(int id) throws NotEnoughCoinException {
        Player player = getCurrentPlayer();

        Character character = model.getCharacter(id);

        if(character.getCost() > player.getNumCoins()) throw new NotEnoughCoinException();
        else{
            character.setOwner(player);
            character.applyEffect();
            player.setNumCoins(player.getNumCoins() - character.getCost());
            model.removeCoins(character.getCost()-1);
            character.setCost(character.getCost()+1);
            model.getActivatedCharacters().add(character.getEffectId());
        }
    }

    /**
     * Method used to move a student
     * @param from      the movable object where the student is
     * @param color     the color of the student the player wants to move
     * @param to        the movable object where the player wants to move the student
     */
    public void moveStudent(Movable from, Student color, Movable to, boolean profCheck) throws InvalidMoveException {
        to.addStudent(from.removeStudent(color));

        if(profCheck){
            model.professorCheck(color);
        }
    }

    /**
     * Randomly chooses the first player and sets it as current player
     */
    public void firstPlayer() {
        int choose = (int) (Math.random() * (model.getNumPlayers()));

        currentPlayer = model.getPlayers().get(choose).getUsername();
    }

    /**
     * At the start of the action phase is called to set the first player
     * based on the assistant cards played
     */
    public void setCurrentPlayer(){
        ArrayList<Integer> values = new ArrayList<>();

        for(Player player : model.getPlayers()){
            values.add(player.getLastCard().getValue());
        }

        int maxValue = values
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow();

        int maxValuePos = values.indexOf(maxValue);

        currentPlayer = model.getPlayers().get(maxValuePos).getUsername();
        model.setCurrentPlayer(activePlayers.indexOf(currentPlayer));
    }


    public String currentPlayer() {
        return currentPlayer;
    }

    public Player getCurrentPlayer(){
        return model.getPlayerByUsername(currentPlayer);
    }

    /**
     * Used on each phase to advance to another player turn
     */
    public void nextPlayer() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);

        if((currentPlayerIndex + 1) < model.getNumPlayers()) currentPlayerIndex++;
        else currentPlayerIndex = 0;

        currentPlayer = activePlayers.get(currentPlayerIndex);
        model.setCurrentPlayer(activePlayers.indexOf(currentPlayer));

        if(gameHandler != null) gameHandler.sendMessageToAllExcept(new GenericMessage(currentPlayer + "is playing his turn! wait...\n"), currentPlayer);
    }

    /**
     * Method called when a user plays a card
     * @param cardPlayed        the card the player decided to play
     * @throws CardAlreadyPlayedException       the player can't play assistant card already played in this round
     */
    public void playCard(AssistantCard cardPlayed) throws CardAlreadyPlayedException {
        Player player = model.getPlayerByUsername(currentPlayer);

        if(!turnCardsPlayed.contains(cardPlayed))
            player.playCard(cardPlayed);

        if(turnCardsPlayed != null) {
            if (turnCardsPlayed.contains(cardPlayed)) throw new CardAlreadyPlayedException();
            else {
                player.playCard(cardPlayed);
                turnCardsPlayed.add(cardPlayed);
            }
        }
    }

    /**
     * Method called at the end of each round to verify if the game has to end
     * @return
     */
    public boolean endingConditionCheck() {
        for (Player player : model.getPlayers()) {
            if(player.getNumTowers() == 0) return true;

            if(player.getDeck().isEmpty()) return true;
        }

        if(model.getBoard().getIslands().size() <= 3) return true;

        if(model.getBoard().getBag().isEmpty()) return true;

        return false;
    }

    public Game getGame() {
        return model;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the current player username
     * @return  the current player username
     */
    public String getCurrentPlayerUsername(){
        return currentPlayer;
    }

    /**
     * Method called when the player wants to move mother nature
     * @param steps number of steps he wants to move mother nature
     * @throws InvalidMotherNatureMovesException
     */
    public void moveMotherNature(int steps) throws InvalidMotherNatureMovesException {
        Player player = model.getPlayerByUsername(currentPlayer);

        if(steps > player.getMotherNatureMoves()) throw new InvalidMotherNatureMovesException();
        else{
            model.getBoard().moveMotherNature(steps);
        }
    }

    /**
     * Get the card played in this round
     * @return      the card played in this round
     */
    public ArrayList<AssistantCard> getTurnCardsPlayed() {
        return turnCardsPlayed;
    }

    public void moveStudentsFromCloud(int cloudId) {
        ArrayList<Student> students = new ArrayList<>();

        Cloud[] clouds = model.getBoard().getClouds();
        try {
            students = clouds[cloudId].getStudentsFromCloud();
        } catch (EmptyCloudException e) {
            e.printStackTrace();
        }

        getCurrentPlayer().getPlayerBoard().fillEntrance(students);
    }


    public void messageReceived (Message message){
        switch (message.getMessageType()) {
            case CLOUD -> {
                ChooseCloudMessage chooseCloudMessage = (ChooseCloudMessage) message;

                moveStudentsFromCloud (chooseCloudMessage.getCloudId());
                newTurn();
            }

            case MOVE_MOTHERNATURE -> {
                MoveMotherNatureMessage moveMotherNatureMessage = (MoveMotherNatureMessage) message;

                try {
                    moveMotherNature(moveMotherNatureMessage.getSteps());
                } catch (InvalidMotherNatureMovesException e) {
                    gameHandler.sendMessage(new ErrorMessage("Invalid mother nature move", ErrorType.INVALID_MOVE), currentPlayer);
                    gameHandler.sendMessage(new AskMotherNature(), currentPlayer);
                }

                playerTurn++;
                if(playerTurn < model.getNumPlayers()){
                    gameHandler.sendMessage(new AskCloud(), currentPlayer);
                } else {
                    playerTurn = 0;
                    phase = 0;
                    newTurn();
                }
            }

            case MOVE_STUDENT -> {
                MoveStudentMessage studentMessage = (MoveStudentMessage) message;
                parseParametersStudent(studentMessage, 3);

                gameHandler.sendMessage(new AskMotherNature(), currentPlayer);
            }

            case PLAY_CARD -> {
                if(playerTurn == 0) turnCardsPlayed.removeAll(turnCardsPlayed);

                PlayCardMessage msg = (PlayCardMessage) message;
                AssistantCard assistantCard = model.getPlayerByUsername(msg.getUsername()).getAssistantCardById(msg.getAssistantCard());
                try {
                    playCard(assistantCard);
                }catch (CardAlreadyPlayedException e){
                    gameHandler.sendMessage(new ErrorMessage(e.getMessage(), ErrorType.DUPLICATE_CARD), message.getUsername());
                    gameHandler.sendMessage(new AskCard(getCurrentPlayer().getDeck(), turnCardsPlayed), currentPlayer);
                }

                playerTurn++;

                if(playerTurn < model.getNumPlayers()){
                    nextPlayer();
                    gameHandler.sendMessage(new AskCard(getCurrentPlayer().getDeck(), turnCardsPlayed), currentPlayer);

                }else{
                    playerTurn = 0;
                    phase++;
                    nextPlayer();
                    actionPhase();
                }
            }

            case ACTIVATE_CHARACTER -> {
                ActivateCharacterMessage activateCharacterMessage = (ActivateCharacterMessage) message;

                switch (activateCharacterMessage.getEffectId()){
                    case 1 -> {
                        gameHandler.sendMessage(new AskStudent(1), message.getUsername());
                    }
                }
            }


        }
    }

    private void parseParametersStudent(MoveStudentMessage studentMessage, int numStudents) {
        Movable from = null;
        Student student = null;
        Movable to = null;
        boolean checkProf = false;

        for(int i = 0; i < numStudents; i ++){
            switch (studentMessage.getFrom()[i]){
                case "entrance" -> {
                    from = model.getPlayerByUsername(studentMessage.getUsername()).getPlayerBoard();
                }
            }

            student = Student.valueOf(studentMessage.getColor()[i]);

            switch (studentMessage.getTo()[i]){
                case "HALL" -> {
                    ColorIntMap cMap = new ColorIntMap();
                    HashMap<Student, Integer> map = cMap.getMap();

                    to = model.getPlayerByUsername(studentMessage.getUsername()).getPlayerBoard().getDinnerRoom()[map.get(student)];

                    checkProf = true;
                }

                case "ISLAND" -> {
                    to = model.getBoard().getIslands().get(studentMessage.getId()[i]);
                }
            }

            try {
                moveStudent(from, student, to, checkProf);
            } catch (InvalidMoveException e) {
                String fromString = studentMessage.getFrom()[i];
                String toString = studentMessage.getTo()[i];
                String studentString = studentMessage.getColor()[i];
                gameHandler.sendMessage(new ErrorMessage("Invalid move! You can't move " + studentString + " from " +
                        fromString + " to " + toString, ErrorType.INVALID_MOVE ), currentPlayer);

                gameHandler.sendMessage(new AskStudent(1), currentPlayer);
            }
        }

    }

    public void addPlayer(String username){
        model.addPlayer(username);
        activePlayers.add(username);
    }

    @Override
    public void update(Message message) {

    }

    public void startGame() {
        if(model.isExpertMode()){
            ReducedCharacter[] reducedCharacters = new ReducedCharacter[3];
            int i = 0;

            for(Character character : model.getCharacters()){
                reducedCharacters[i] = new ReducedCharacter(character.getCost(), character.getEffectId(), character.getDesc());
                i++;
            }

            for(Player player : model.getPlayers()){
                gameHandler.sendMessage(new ReducedModelMessage(player.getUsername(), player.getTowerColor(), reducedCharacters, model.isExpertMode()), player.getUsername());
            }
        }
        else{
            for(Player player : model.getPlayers()){
                gameHandler.sendMessage(new ReducedModelMessage(player.getUsername(), player.getTowerColor()), player.getUsername());
            }
        }

        String[] owner = new String[12];
        int[][] numStudents = new int[12][5];

        for(int i = 0; i < 12; i++){
            numStudents[i] = model.getBoard().getIslands().get(i).getNumStudents();
            if(model.getBoard().getIslands().get(i).getOwner()!=null)
            {
                owner[i] = model.getBoard().getIslands().get(i).getOwner().getUsername();
            }
            else owner[i] = "No owner";
        }

        for(Player player : model.getPlayers()){
            int[] entrance = new int[5];
            int[] hall = new int[5];

            for(int i = 0; i < 5; i++){
                entrance = player.getPlayerBoard().getEntranceStudents();
                hall[i] = player.getPlayerBoard().getDinnerRoom()[i].getNumStudents();
            }

            ReducedPlayerBoard reducedPlayerBoard = new ReducedPlayerBoard(entrance, hall);

            gameHandler.sendMessage(new BoardMessage(numStudents, model.getBoard().getClouds(), owner, reducedPlayerBoard, model.getBoard().getMotherNature()), player.getUsername());
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameHandler.sendMessageToAll(new StartGame());

        newTurn();
    }

    private void newTurn() {
        if(model.isExpertMode()) model.getActivatedCharacters().clear();



        if(turn == 1) firstPlayer();
        else setCurrentPlayer();
        turnCardsPlayed.clear();


        gameHandler.sendMessageToAllExcept(new GenericMessage(currentPlayer + " is playing his turn! wait...\n"), currentPlayer);
        gameHandler.sendMessage(new GenericMessage("Clouds have been filled! \n"), currentPlayer);
        gameHandler.sendMessage(new AskCard(getCurrentPlayer().getDeck(), turnCardsPlayed), currentPlayer);
    }

    private void actionPhase(){
        gameHandler.sendMessage(new AskStudent(3), currentPlayer);
    }


}
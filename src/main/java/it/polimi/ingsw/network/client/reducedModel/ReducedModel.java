package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** This class contains the reduced part of the game logic with the information the client needs */

public class ReducedModel implements Serializable {
    private List<String> username;
    private ReducedBoard reducedBoard;
    private String playerUsername;
    private TowerColor color;
    private ReducedCharacter[] reducedCharacters;
    private String currentPlayer;
    private boolean isExpertMode;
    private int turnMaxSteps;
    private boolean isActive;
    private List<AssistantCard> deck;
    private List<AssistantCard> turnCards;
    private int numCoins;
    private String[] professorOwners;
    private ColorIntMap colorIntMap = new ColorIntMap();
    private Map<Student, Integer> getIntFromStudent = colorIntMap.getMap();
    private int wizardId;

    /**
     * Default constructor
     * @param username       username's list
     * @param color          color of the tower
     * @param reducedCharacters         lighter version of the character
     * @param reducedBoard       lighter version of the player board
     * @param isExpertMode        true if the expert mode is ON
     */
    public ReducedModel(List<String> username, TowerColor color, ReducedCharacter[] reducedCharacters,ReducedBoard reducedBoard, boolean isExpertMode) {
        this.username = username;
        this.color = color;
        this.reducedCharacters = reducedCharacters;
        this.reducedBoard = reducedBoard;
        this.professorOwners = new String[5];
        this.isExpertMode = isExpertMode;
        numCoins = 3;
    }

    /**
     * Default constructor
     * @param username       username's list
     * @param color          color of the tower
     * @param reducedBoard       lighter version of the player board
     */
    public ReducedModel(List<String> username, TowerColor color, ReducedBoard reducedBoard){
        this.username = username;
        this.color = color;
        this.reducedBoard = reducedBoard;
        this.professorOwners = new String[5];
        isExpertMode = false;
    }

    public int getNumCoins() {
        return this.numCoins;
    }

    public void addCoin(){
        this.numCoins++;
    }

    public void removeCoin(){
        this.numCoins--;
    }

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getUsername() {
        return username;
    }

    public TowerColor getColor() {
        return color;
    }

    public ReducedCharacter[] getReducedCharacters() {
        return reducedCharacters;
    }

    public ReducedBoard getReducedBoard() {
        return reducedBoard;
    }

    /** This method sets the reduced board **/
    public void setReducedBoard(ReducedBoard reducedBoard) {
        this.reducedBoard = reducedBoard;

        if(username.size() == 2) reducedBoard.getPlayerBoard().setNumTowers(8);
        else reducedBoard.getPlayerBoard().setNumTowers(6);
    }

    public void setMaxSteps(int steps){
        this.turnMaxSteps = steps;
    }

    public int getMaxSteps() {
        return turnMaxSteps;
    }

    /** This method activates the character's effect **/
    public void activateCharacter(int id){
        Arrays.stream(reducedCharacters).filter(reducedCharacter -> reducedCharacter.getEffectId() == id).collect(Collectors.toList())
                .get(0).activate();


    }

    /** This method moves the student **/
    public void moveStudent(String from, String student, String to, int id) {
        if(from.equals("ENTRANCE")) reducedBoard.getPlayerBoard().removeEntranceStudent(student);

        if(to.equals("ISLAND")) reducedBoard.addStudentToIsland(id, Student.valueOf(student));

        if(to.equals("DINNER")) reducedBoard.getPlayerBoard().addHallStudent(student);
    }

    public void setDeck(List<AssistantCard> assistantCards) {
        this.deck = assistantCards;
    }

    public List<AssistantCard> getDeck() {
        return deck;
    }

    public List<AssistantCard> getTurnCards() {
        return turnCards;
    }

    public void setTurnCards(List<AssistantCard> turnCards) {
        this.turnCards = turnCards;
    }


    public void setUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setProfOwner(String professorOwner, Student color) {
        this.professorOwners[getIntFromStudent.get(color)] = professorOwner;
    }

    public int getWizardId() {
        return wizardId;
    }

    public void setWizardId(int wizardId) {
        this.wizardId = wizardId;
    }

    /** This method is useful to get the character by id **/
    public ReducedCharacter getCharacterById(int id){

        ReducedCharacter character = Arrays.stream(reducedCharacters)
                .filter(c -> c.getEffectId() == id)
                .collect(Collectors.toList())
                .get(0);

        return character;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }
}

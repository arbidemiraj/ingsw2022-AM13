package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class that represents the player
 */
public class Player {

	private PlayerBoard playerBoard;
	private int motherNatureMoves;
	private final String username;
	private TowerColor towerColor;
	private int numTowers;
	private ArrayList<AssistantCard> deck;
	private Wizard wizard;
	private int influence = 0;
	private AssistantCard lastCard;
	private int numCoins = 0;

	/**
	 * Default constructor
	 * @param numTowers number of towers the player has at the start
	 * @param username the username of the player
	 */

	public Player(int numTowers, String username) {
		this.numTowers = numTowers;
		this.username = username;
		playerBoard = new PlayerBoard();
		chooseWizard(wizard);
		lastCard = new AssistantCard(0,0);

	}

	/**
	 * creates the deck
	 * @param wizard the wizard the player has
	 */

	public void chooseWizard(Wizard wizard){
		this.wizard = wizard;
		createDeck();
	}

	/**
	 * returns the number of students of the given color in the dinner room
	 * @param color the color you want to know the number of students
	 * @return the number of students of the given color in the dinner room
	 */
	public int getNumStudents(Student color) {
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		int colorPos = studentColor.get(color);

		return playerBoard.getDinnerRoom()[colorPos].getNumStudents();
	}

	/**
	 * creates the deck of assistant card of the player with a json file
	 */
	private void createDeck() {
		deck = new ArrayList<>();

			InputStream is = getClass().getClassLoader().getResourceAsStream("AssistantCards.json");

			Scanner myReader = new Scanner(is);
			String data = myReader.nextLine();

			Gson gson = new Gson();
			Type userListType = new TypeToken<ArrayList<AssistantCard>>(){}.getType();
			deck = gson.fromJson(data, userListType);

			myReader.close();
	}

	public PlayerBoard getPlayerBoard() {
		return playerBoard;
	}

	/**
	 * plays the given assistant card
	 * @param cardPlayed the card played
	 */
	public void playCard(AssistantCard cardPlayed){
		this.lastCard = cardPlayed;
		deck.remove(cardPlayed);
		motherNatureMoves = cardPlayed.getMaxMotherNatureMoves();
	}

	public void setInfluenceValue(int influence) {
		this.influence=influence;
	}

	public void addInfluence(){
		this.influence++;
	}

	public int getNumTowers() {
		return numTowers;
	}

	public int getInfluenceValue() {
		return this.influence;
	}

	public ArrayList<AssistantCard> getDeck() {
		return deck;
	}

	/**
	 * returns the number of students in the dinner room
	 * @return
	 */
	public int numStudents(){
		int numStudents = 0;
		for(int i = 0; i < 5; i++){
			numStudents += playerBoard.getDinnerRoom()[i].getNumStudents();
		}

		return numStudents;
	}

	/**
	 * increases the number of coins of the player
	 */
	public void addCoin(){
		numCoins++;
	}

	public int getNumCoins() {
		return numCoins;
	}

	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}

	public String getUsername() {
		return username;
	}

	public int getMotherNatureMoves() {
		return motherNatureMoves;
	}

	public AssistantCard getLastCard() {
		return lastCard;
	}

	public void setTowerColor(TowerColor towerColor) {
		this.towerColor = towerColor;
	}

	/**
	 * returns the assistant card with the given id
	 * @param assistantCardId the id of the card you want
	 * @return the assistant card with the given id
	 */
    public AssistantCard getAssistantCardById(int assistantCardId) {
		return deck
				.stream()
				.filter(a -> a.getValue() == assistantCardId)
				.collect(Collectors.toList())
				.get(0);
    }

	public TowerColor getTowerColor() {
		return towerColor;
	}

    public void removeTower() {
		numTowers--;
    }

    public void addTower() {
		numTowers++;
    }
}

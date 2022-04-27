package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizard;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

	private PlayerBoard playerBoard;
	private int motherNatureMoves;
	private final String nickname;
	private TowerColor towerColor;
	private int numTowers;
	private ArrayList<AssistantCard> deck;
	private Wizard wizard;
	private int influence = 0;
	private ArrayList<AssistantCard> discardPile;
	private int numCoins = 0;

	public Player(TowerColor towerColor, int numTowers, String nickname) {
		this.numTowers = numTowers;
		this.towerColor = towerColor;
		this.nickname = nickname;
		playerBoard = new PlayerBoard();
		chooseWizard(wizard);
	}

	public void chooseWizard(Wizard wizard){
		this.wizard = wizard;
		createDeck();
	}

	public int getNumStudents(Student color) {
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		int colorPos = studentColor.get(color);

		return playerBoard.getDinnerRoom()[colorPos].getNumStudents();
	}

	private void createDeck() {
		deck = new ArrayList<>();

		deck.add(new AssistantCard(1,1));
		deck.add(new AssistantCard(2,1));
		deck.add(new AssistantCard(3,2));
		deck.add(new AssistantCard(4,2));
		deck.add(new AssistantCard(5,3));
		deck.add(new AssistantCard(6,3));
		deck.add(new AssistantCard(7,4));
		deck.add(new AssistantCard(8,4));
		deck.add(new AssistantCard(9,5));
		deck.add(new AssistantCard(10,5));
	}

	public PlayerBoard getPlayerBoard() {
		return playerBoard;
	}

	public void playCard(AssistantCard cardPlayed){
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
		return influence;
	}

	public AssistantCard getLastCard() {
		if(discardPile != null) return discardPile.get(discardPile.size());

		return null;
	}

	public ArrayList<AssistantCard> getDeck() {
		return deck;
	}

	public int numStudents(){
		int numStudents = 0;
		for(int i = 0; i < 5; i++){
			numStudents += playerBoard.getDinnerRoom()[i].getNumStudents();
		}

		return numStudents;
	}

	public void addCoin(){
		numCoins++;
	}

	public int getNumCoins() {
		return numCoins;
	}

	public void setNumTowers(int numTowers) {
		this.numTowers = numTowers;
	}

	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}

	public String getNickname() {
		return nickname;
	}

	public int getMotherNatureMoves() {
		return motherNatureMoves;
	}

	public void setMotherNatureMoves(int motherNatureMoves) {
		this.motherNatureMoves = motherNatureMoves;
	}
}

package it.polimi.ingsw;

import java.util.ArrayList;

public class Player {

	private PlayerBoard playerBoard;

	private TowerColor towerColor;

	private int numTowers;

	private ArrayList<AssistantCard> deck;

	private Wizard wizard;

	private int influence = 0;

	private ArrayList<AssistantCard> discardPile;

	private int numCoins = 0;

	public Player(TowerColor towerColor, int numTowers) {
		this.numTowers = numTowers;
		this.towerColor = towerColor;
		playerBoard = new PlayerBoard();
		chooseWizard(wizard);
	}

	public void chooseWizard(Wizard wizard){
		this.wizard = wizard;
		createDeck();
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

	public void playCard(ArrayList<AssistantCard> cardsPlayed, AssistantCard cardPlayed) throws CardAlreadyPlayedException {
		if(cardsPlayed != null) {
			if (cardsPlayed.contains(cardPlayed)) throw new CardAlreadyPlayedException();
			else {
				deck.remove(cardPlayed);
			}
		}
		else deck.remove(cardPlayed);
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
}

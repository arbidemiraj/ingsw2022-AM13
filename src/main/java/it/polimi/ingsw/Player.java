package it.polimi.ingsw;

import java.util.ArrayList;

public class Player {

	private PlayerBoard playerBoard;

	private TowerColor towerColor;

	private int numTowers;

	private ArrayList<AssistantCard> deck;

	private Wizard wizard;

	private int influence;

	private ArrayList<AssistantCard> discardPile;

	private int numCoins = 0;

	public Player(TowerColor towerColor, int numTowers) {
		this.numTowers = numTowers;
		this.towerColor = towerColor;
		playerBoard = new PlayerBoard();
	}

	public void chooseWizard(Wizard wizard){
		this.wizard = wizard;
		createDeck();
	}

	private void createDeck() {
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
		if(cardsPlayed.contains(cardPlayed)) throw new CardAlreadyPlayedException();
		else{
			deck.remove(cardPlayed);
		}
	}

	public void setInfluenceValue(int influence) {
		this.influence=influence;
	}

	public void addInfluence(){
		influence++;
	}

	public int getNumTowers() {
		return numTowers;
	}

	public int getInfluenceValue() {
		return influence;
	}

	public AssistantCard getLastCard() {
		return discardPile.get(discardPile.size());
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
}

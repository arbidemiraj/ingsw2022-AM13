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

	private int numCoins;

	public Player(TowerColor towerColor, int numTowers) {
		this.numTowers = numTowers;
		this.towerColor = towerColor;
		playerBoard = new PlayerBoard();
	}

	public void chooseWizard(Wizard wizard){
		this.wizard = wizard;
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

}

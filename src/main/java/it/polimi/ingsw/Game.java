package it.polimi.ingsw;

import java.util.ArrayList;

public class Game {

	private Player currentPlayer;

	private Player influencePlayer;

	private GameTable table;

	private boolean expertMode;

	private Character characters[];

	private ArrayList<AssistantCard> cardsPlayed;


	public Game(int numPlayers, boolean expertMode) {
		table = new GameTable(numPlayers);

		firstPlayer();
	}

	public void playRound() {
		planningPhase();
	}

	private boolean endingConditionCheck() {
		return false;
	}

	private void planningPhase() {
	}

	public void playCard(AssistantCard cardPlayed) {
	}

	private void actionPhase() {

	}

	private void unifyIslands() {

	}

	private void controlling() {

	}

	public void firstPlayer() {
		int i = (int) Math.random() * 2;
		currentPlayer = table.getPlayers()[i];
	}

	public void currentPlayer() {

	}

	public void professorCheck() {

	}

	public void professorCheck(Character character) {

	}

	private void conquering() {

	}

	public void conquering(Character character) {

	}

	private void influence() {

	}

	private void influence(Character character) {

	}

}

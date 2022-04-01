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
		this.expertMode = expertMode;

		if(expertMode){
			int id;
			int availableEffects[]={1,2,3,4,5,6,7,11};

			for(int i = 0; i < 3; i++){
				id = chooseEffect(availableEffects);
				characters[i] = new Character(this, id);
			}
		}

		firstPlayer();
	}

	private int chooseEffect(int[] availableEffect) {
		int effect, random;

		do {
			random = (int)(Math.random()*8);
			effect = availableEffect[random];

			if(effect != 0){
				availableEffect[random] = 0;
				return effect;
			}
		} while (effect == 0 );

		return 0;
	}

	public void playRound() {
		planningPhase();
	}

	private boolean endingConditionCheck() {
		return false;
	}

	private void planningPhase() {
		table.extractStudents(3);
		table.getClouds()[0].addStudents(table.getExtractedStudents());
		table.extractStudents(3);
		table.getClouds()[1].addStudents(table.getExtractedStudents());

		cardsPlayed.clear();
		AssistantCard cardPlayed = new AssistantCard();
		playCard(cardPlayed);
	}

	public void playCard(AssistantCard cardPlayed) {

		try {
			currentPlayer.playCard(cardsPlayed, cardPlayed);
		} catch (CardAlreadyPlayedException e) {
			e.printError();
		}

		cardsPlayed.add(cardPlayed);
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

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
		//step 1 move students
		//move students to hall
		DinnerRoomRow.addStudent();
		table.
		//move students to isle
		Island.getStudents();

		//step 2 move mother Nature and conquere islands
		table.moveMotherNature();

		//step 3 choose cloud card and take students
		//al momento presente nella planning phase
	}


	//calculate influence
	public int influence (){
		int influence=0;
		//recupero gli studenti dell'isola in cui si trova madre natura
		table.getMotherNature().getStudents();
		//sommo l'influenza di tutti gli studenti controllati dal giocatore preso in considerazione
		for(int i=0;i<5;i++){//scorro i 5 colori
			//incremento solo se il player controlla il prof
			if(colore==player.professore)
				//incremento l'influenza in base al numero di studenti di quel colore
				influence=influence+i.students;
		}
		//controllo che la torre sia del player
		owner.addInfluence();
		influence++;

		return influence;
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

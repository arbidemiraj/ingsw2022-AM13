package it.polimi.ingsw;

import java.util.ArrayList;

public class Game {

	private int currentPlayer; //represents the position of currentPlayer in players Array

	private Player influencePlayer = null; //the player with the highest influence

	private GameTable table;

	private boolean expertMode;

	private Character characters[];

	private ArrayList<AssistantCard> cardsPlayed; //each round has a list of Card containing the card played in the round

	private int numPlayers;

	public Game(int numPlayers, boolean expertMode) {
		this.expertMode = expertMode;
		this.numPlayers = numPlayers;
		table = new GameTable(numPlayers);

		if(expertMode){
			//creates 3 character cards
			int id;
			int availableEffects[]={1,2,3,4,5,6,7,11};

			for(int i = 0; i < 3; i++){
				id = chooseEffect(availableEffects);
				characters[i] = new Character(this, id);
			}
		}

		int numRound = 1;

		while(!endingConditionCheck()){
			playRound(numRound);
		}
	}

	public GameTable getTable() {
		return table;
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

	public void playRound(int numRound) {
		if(numRound == 1) firstPlayer();
		Player player = table.getPlayers()[currentPlayer];

		planningPhase();

		setCurrentPlayer();
		for(int i = 0; i < numPlayers; i++) {
			actionPhase();
			nextPlayer();
		}

		numRound++;
	}

	private boolean endingConditionCheck() {
		for (Player player : table.getPlayers()) {
			if(player.getNumTowers() == 0) return true;

			if(player.getDeck().isEmpty()) return true;
		}

		if(table.getIslands().size()<=3) return true;

		if(table.getBag().isEmpty()) return true;


		return false;
	}

	private void planningPhase() {
		table.extractStudents(3);
		table.getClouds()[0].addStudents(table.getExtractedStudents());
		table.extractStudents(3);
		table.getClouds()[1].addStudents(table.getExtractedStudents());

		cardsPlayed.clear();

		for(int i = 0; i < numPlayers; i++){
			Player player = table.getPlayers()[currentPlayer];
			AssistantCard cardPlayed = new AssistantCard();
			playCard(cardPlayed, player);
			nextPlayer();
		}

	}

	public void playCard(AssistantCard cardPlayed, Player player) {

		try {
			player.playCard(cardsPlayed, cardPlayed);
		} catch (CardAlreadyPlayedException e) {
			e.printError();
		}

		cardsPlayed.add(cardPlayed);
	}

	private void actionPhase() {
		for(int i = 0; i < numPlayers; i++) {
			//move student from entrance to dinner/island
			//get input from user
			//move mother nature

			if (table.getMotherNatureIsland().getOwner() == null) controlling();
			else conquering();

			//choose cloud
			//get input from use
		}
	}

	private void setInfluencePlayer(){
		influence();

		for(int i = 0; i < numPlayers; i++){
			if(influencePlayer.getInfluenceValue()<table.getPlayers()[i].getInfluenceValue()){
				influencePlayer = table.getPlayers()[i];
			}
		}

	}

	//calculate influence
	private void influence (){

		for(Player player : table.getPlayers()) player.setInfluenceValue(0); //reset influence

		//number of students for each color in the island, es. numStudent[0] = num of yellow students
		int numStudents[] = table.getMotherNatureIsland().getNumStudents();

		//add influence to each player if the player is the owner, based on the number of students
		for(Player player: table.getPlayers()){
			for(int i = 0; i < 5; i++) table.getProfessors()[i].getOwner().addInfluence();
		}

		//add influence if the player has a tower
		table.getMotherNatureIsland().getOwner().addInfluence();
	}

	//merges the values of the two given islands
	private UnifiedIsland unifyIslands(Island A1, Island A2) {
		unifyIslands()= new ArrayList<>;
		//for cicle to sum all students for all colours
		for (int i=0;i<5;i++){
			unifyIslands().setStudents(i)=A1.getNumStudents(i)+A2.getNumStudents(i);
		}
		//need to sum the tower
		return unifyIslands();
	}


	//verify that the current island can be merged with the other
	private void Merge(){
		//tutte le variabili vanno sistemate, l'unione di due isole dove va a finire? dove verifico l'unione sennò?
		Island A1,A2,A3; //indexes of the islands to compare
		A1=table.getMotherNatureIsland(-1);//previous island
		A2=table.getMotherNatureIsland();//current island
		A3=table.getMotherNatureIsland(+1);//next island
		if(A1.getOwner()==A2.getOwner())
			unifyIslands();
		if (A2.getOwner()==A3.getOwner())
			unifyIslands();
	}

	private void controlling() {
		setInfluencePlayer();

		if(influencePlayer!=null){
			table.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void firstPlayer() {
		currentPlayer = (int) Math.random() * numPlayers;
	}

	public void nextPlayer() {
		if((currentPlayer+1)<numPlayers) currentPlayer++;
		else currentPlayer = 0;
	}

	private void setCurrentPlayer(){
		//controls the assistant card played value to set the currentPlayer
		int value1, value2, i = 0;

		value1 = table.getPlayers()[currentPlayer].getLastCard().getValue();

		for(Player player : table.getPlayers()){
			value2 = player.getLastCard().getValue();
			if(value2>value1) currentPlayer = i;
			i++;
		}
	}


	public void professorCheck(Character character) {

	}

	private void conquering() {
		setInfluencePlayer();

		if(influencePlayer != table.getMotherNatureIsland().getOwner()){
			table.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void conquering(Character character) {

	}

	private void influence(Character character) {

	}

}

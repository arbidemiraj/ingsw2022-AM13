package it.polimi.ingsw;

import java.util.ArrayList;

public class Game {

	private int currentPlayer; //represents the position of currentPlayer in players Array

	private Player influencePlayer = null; //the player with the highest influence

	private final GameTable table;

	private int generalSupply;

	private int motherNatureMoves = 0;

	private boolean expertMode;

	private Character[] characters;

	private ArrayList<AssistantCard> cardsPlayed; //each round has a list of Card containing the card played in the round

	private final int numPlayers;

	public Game(int numPlayers){
		this.numPlayers = numPlayers;
		table = new GameTable(numPlayers);

		/*int numRound = 1;

		do{
			playRound(numRound);
		}while(!endingConditionCheck());
		 */
	}

	public Game(int numPlayers, boolean expertMode) {
		this.expertMode = expertMode;
		this.numPlayers = numPlayers;
		table = new GameTable(numPlayers);


		setupExpertMode();
		for(Player player : table.getPlayers()) player.addCoin();

		int numRound = 1;

		while(!endingConditionCheck()){
			playRound(numRound);
		}
	}

	private void setupExpertMode() {
		//creates 3 character cards
		int id;
		int[] availableEffects={1,2,3,4,5,6,8,11};

		generalSupply = 20-numPlayers;

		for(int i = 0; i < 3; i++){
			id = chooseEffect(availableEffects);
			characters[i] = new Character(this, id);
		}
	}

	public void playRound(int numRound) {
		if(numRound == 1) firstPlayer();

		planningPhase();

		setCurrentPlayer();
		for(int i = 0; i < numPlayers; i++) {
			actionPhase();
			nextPlayer();
		}

		numRound++;
	}

	private void planningPhase() {
		// fill the clouds

		table.getClouds()[0].addStudents(table.extractStudents(3));
		table.getClouds()[1].addStudents(table.extractStudents(3));

		//cards played has the assistant card played in this round, so at the start of it is cleared
		cardsPlayed.clear();

		//each player plays a card
		for(int i = 0; i < numPlayers; i++){
			Player player = table.getPlayers()[currentPlayer];

			//cardPlayed will be the cardPlayed from the player
			AssistantCard cardPlayed = new AssistantCard(2,2);
			playCard(cardPlayed, player);
			nextPlayer();
		}

	}

	private void actionPhase() {
		for(int i = 0; i < numPlayers; i++) {
			//move student from entrance to dinner/island
			//table.moveToDinner(student) or table.moveToIsland(student, chosenIsland)
			//for each move of student -> professorCheck(color);

			//get input from user
			int steps = 0;
			table.moveMotherNature(steps);

			//add control for effects to call overloaded methods
			if (table.getMotherNatureIsland().getOwner() == null) controlling();
			else conquering();

			mergeCheck();

			//choose cloud
			//table.moveStudentsFromCloud(numCloud);
			//get input from user
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

	private boolean endingConditionCheck() {
		for (Player player : table.getPlayers()) {
			if(player.getNumTowers() == 0) return true;

			if(player.getDeck().isEmpty()) return true;
		}

		if(table.getIslands().size()<=3) return true;

		if(table.getBag().isEmpty()) return true;

		return false;
	}

	public void addMotherNatureMoves() {
		this.motherNatureMoves += 2;
	}

	public void playCard(AssistantCard cardPlayed, Player player) {

		try {
			player.playCard(cardsPlayed, cardPlayed);
		} catch (CardAlreadyPlayedException e) {
			e.printError();
		}

		cardsPlayed.add(cardPlayed);
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
    void influence(){
		if(!table.getMotherNatureIsland().isNoEntryTile()) {
			for (Player player : table.getPlayers()) player.setInfluenceValue(0); //reset influence

			//number of students for each color in island, es. numStudent[0] = num of yellow students
			int[] numStudents = table.getMotherNatureIsland().getNumStudents();

			//add influence to each player if the player is the owner, based on the number of students
			for (Player player : table.getPlayers()) {
				for (int i = 0; i < 5; i++) table.getProfessors()[i].getOwner().addInfluence();
			}

			//add influence if the player has a tower
			table.getMotherNatureIsland().getOwner().addInfluence();
		}
	}

	public void influence (Island island){

		for(Player player : table.getPlayers()) player.setInfluenceValue(0); //reset influence

		//number of students for each color in island, es. numStudent[0] = num of yellow students
		int[] numStudents = island.getNumStudents();

		//add influence to each player if the player is the owner, based on the number of students
		for(Player player: table.getPlayers()){
			for(int i = 0; i < 5; i++) table.getProfessors()[i].getOwner().addInfluence();
		}

		//add influence if the player has a tower
		table.getMotherNatureIsland().getOwner().addInfluence();
	}

	//merges the values of the two given islands
	private void unifyIslands(Island island1, Island island2) {
		ArrayList<Student> students1;
		ArrayList<Student> students2;
		int numIslands = island1.getIslandState().getNumIslands();

		UnifiedIsland unify = new UnifiedIsland(numIslands);

		//add student to the island with motherNature
		students1 = island1.getStudents();
		students2 = island2.getStudents();
		students1.addAll(students2);

		//sums the tower
		unify.addIsland();
		island1.changeState(unify);
	}

	//verify that the current island can be merged with the other
	private void mergeCheck(){
		Island island1, island2, island3; //indexes of the islands to compare
		int motherNature, previous, next;

		motherNature = table.getMotherNature();


		if(motherNature == table.getIslands().size()-1){
			next = 0;
		}
		else next = motherNature + 1;

		if(motherNature == 0){
			previous = table.getIslands().size()-1;
		}
		else previous = motherNature - 1;

		island1 = table.getIslands().get(previous);//previous island
		island2 = table.getMotherNatureIsland();//current island
		island3 = table.getIslands().get(next);//next island

		if(island2.getOwner().equals(island1.getOwner())) {
			unifyIslands(island2, island1);
			table.getIslands().remove(previous);
		}
		if(island2.getOwner().equals(island3.getOwner())) {
			unifyIslands(island2, island3);
			table.getIslands().remove(next);
		}
	}

	private void controlling() {
		setInfluencePlayer();

		if(influencePlayer!=null){
			table.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void firstPlayer() {
		currentPlayer = (int) (Math.random() * numPlayers);
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


	public void professorCheck(Student color){
		int[] numStudents = new int[numPlayers];
		int highestStudent = 0, i = 0, j = 0, colorPos = 0, countHighest = 0;
		Player owner;

		if(color == Student.YELLOW) colorPos = 0;
		if(color == Student.BLUE) colorPos = 1;
		if(color == Student.GREEN) colorPos = 2;
		if(color == Student.PINK) colorPos = 3;
		if(color == Student.RED) colorPos = 4;

		//get number of students for each player based on the color
		for(Player player : table.getPlayers()){
			numStudents[i] = player.getPlayerBoard().getDinnerRoom()[colorPos].getNumStudents();
		}

		for( i = 0; i < numPlayers; i++){
			if(numStudents[i] >= highestStudent){
				if(highestStudent == numStudents[i]){
					countHighest++;
				}

				highestStudent = numStudents[i];

				j = i;
			}
		}

		if(highestStudent != 0 && countHighest == 1){
			owner = table.getPlayers()[j];
			table.getProfessors()[colorPos].setOwner(owner);
		}
	}

	public void professorCheck(Character character, Student color) {
		if(character.getEffectId() != 2) professorCheck(color);
		else{
			int[] numStudents = new int[numPlayers];
			int highestStudent = 0, i = 0, j = 0, l=0, colorPos = 0;
			Player[] highest = new Player[numPlayers];
			Player owner;

			if(color == Student.YELLOW) colorPos = 0;
			if(color == Student.BLUE) colorPos = 1;
			if(color == Student.GREEN) colorPos = 2;
			if(color == Student.PINK) colorPos = 3;
			if(color == Student.RED) colorPos = 4;

			//get number of students for each player based on the color
			for(Player player : table.getPlayers()){
				numStudents[i] = player.getPlayerBoard().getDinnerRoom()[colorPos].getNumStudents();
			}

			//checks who are the player with highest num of students
			for( i = 0; i < numPlayers; i++){
				if(numStudents[i] >= highestStudent){
					if(highestStudent == numStudents[i]){
						highest[l] = table.getPlayers()[i];
						l++;
					}
					highestStudent = numStudents[i];
					j = i;
				}
			}

			//if one of the player with the highest num of student has activated the card gets the professor
			for (Player player : highest){
				if(character.getOwner().equals(player)){
					table.getProfessors()[colorPos].setOwner(player);
				}
			}
		}
	}

	private void conquering() {
		setInfluencePlayer();

		if(influencePlayer != table.getMotherNatureIsland().getOwner()){
			table.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void conquering(Character character) {
		if(character.getEffectId() != 6) conquering();
		else{
			setInfluencePlayer(character);
		}
	}

	private void setInfluencePlayer(Character character){
		influence(character);

		for(int i = 0; i < numPlayers; i++){
			if(influencePlayer.getInfluenceValue()<table.getPlayers()[i].getInfluenceValue()){
				influencePlayer = table.getPlayers()[i];
			}
		}
	}

	private void influence(Character character){
		if(character.getEffectId() == 7){
			influence();
			character.getOwner().addInfluence();
			character.getOwner().addInfluence(); //additional 2 influence points
		}
		else if(character.getEffectId()!=6) {
			influence();
		} else{
			for(Player player : table.getPlayers()) player.setInfluenceValue(0); //reset influence

			//number of students for each color in island, es. numStudent[0] = num of yellow students
			int[] numStudents = table.getMotherNatureIsland().getNumStudents();

			//add influence to each player if the player is the owner, based on the number of students
			for(Player player: table.getPlayers()){
				for(int i = 0; i < 5; i++) table.getProfessors()[i].getOwner().addInfluence();
			}
		}


	}

	public void moveStudent(Student student){

	}

	public Character[] getCharacters() {
		return characters;
	}

	public void activateCharacter(Character character, Player player, Island chosenIsland) throws NotEnoughCoinException {
		if(character.getCost()>player.getNumCoins()) throw new NotEnoughCoinException();
		else{
			character.setOwner(player);
			character.applyEffect(chosenIsland);
		}
	}

	public void activateCharacter(Character character, Player player, Student chosenStudent) throws NotEnoughCoinException {
		if(character.getCost()>player.getNumCoins()) throw new NotEnoughCoinException();
		else{
			character.setOwner(player);
			character.applyEffect(chosenStudent);
		}
	}

	public void activateCharacter(Character character, Player player) throws NotEnoughCoinException {
		if(character.getCost()>player.getNumCoins()) throw new NotEnoughCoinException();
		else{
			character.setOwner(player);
			character.applyEffect();
		}
	}

}

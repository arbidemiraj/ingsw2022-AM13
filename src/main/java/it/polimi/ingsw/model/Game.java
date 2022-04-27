package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.NotEnoughCoinException;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Game {

	private int currentPlayer; //represents the position of currentPlayer in players Array

	private Player influencePlayer; //the player with the highest influence

	private final GameBoard board;

	private int generalSupply;

	private int motherNatureMoves = 0;

	private boolean expertMode;

	private Character[] characters;

	private ArrayList<AssistantCard> cardsPlayed; //each round has a list of Card containing the card played in the round

	private final int numPlayers;

	public Game(int numPlayers){
		board = new GameBoard(numPlayers);
		this.numPlayers = numPlayers;

	}

	public Game(int numPlayers, boolean expertMode) {
		this.expertMode = expertMode;
		this.numPlayers = numPlayers;
		board = new GameBoard(numPlayers);
		board.prepareGame();

		setupExpertMode();
		for(Player player : board.getPlayers()) player.addCoin();

	}

	public void setupExpertMode() {
		//creates 3 character cards
		int id;
		characters = new Character[3];

		int[] availableEffects={1, 2, 3, 4, 5, 6, 8, 11};

		generalSupply = 20 - numPlayers;

		for(int i = 0; i < 3; i++){
			id = chooseEffect(availableEffects);
			characters[i] = new Character(this, id);
		}
	}


	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public GameBoard getBoard() {
		return board;
	}

	public int chooseEffect(int[] availableEffect) {
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

	public void addMotherNatureMoves() {
		motherNatureMoves = board.getMotherNature();
		this.motherNatureMoves += 2;
		board.setMotherNature(motherNatureMoves);
	}



	private void setInfluencePlayer(){
		influence(board.getMotherNatureIsland());

			for(int i = 0; i < numPlayers; i++){

				if(influencePlayer == null || influencePlayer.getInfluenceValue() < board.getPlayers().get(i).getInfluenceValue()){
					influencePlayer = board.getPlayers().get(i);
				}
			}

	}

	public void influence (Island island) {
		if (!island.isNoEntryTile()) {
			for (Player player : board.getPlayers()) player.setInfluenceValue(0); //reset influence

			//number of students for each color in island, es. numStudent[0] = num of yellow students
			int[] numStudents = island.getNumStudents();

			//add influence to each player if the player is the owner, based on the number of students
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < numStudents[i]; j++) {
					if (board.getProfessors()[i].getOwner() != null) board.getProfessors()[i].getOwner().addInfluence();
				}

			}

			//add influence if the player has a tower
			if (board.getMotherNatureIsland().getOwner() != null)
				board.getMotherNatureIsland().getOwner().addInfluence();
		}
	}

	//merges the values of the two given islands
	public void unifyIslands(Island island1, Island island2) {
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
	public void mergeCheck(){
		Island island1, island2, island3; //indexes of the islands to compare
		int motherNature, previous, next;

		motherNature = board.getMotherNature();


		if(motherNature == board.getIslands().size()-1){
			next = 0;
		}

		else next = motherNature + 1;

		if(motherNature == 0){
			previous = board.getIslands().size()-1;
		}
		else previous = motherNature - 1;

		island1 = board.getIslands().get(previous);//previous island
		island2 = board.getMotherNatureIsland();//current island
		island3 = board.getIslands().get(next);//next island

		if(island2.getOwner().equals(island1.getOwner())) {
			unifyIslands(island2, island1);
			board.setMotherNature(motherNature - 1);
			board.getIslands().remove(previous);
		}
		if(island2.getOwner().equals(island3.getOwner())) {
			unifyIslands(island2, island3);
			board.getIslands().remove(next);
		}
	}

	private void controlling() {
		setInfluencePlayer();

		if(influencePlayer!=null){
			board.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public int getNumPlayers(){
		return board.getPlayers().size();
	}

	public void professorCheck(Student color){
		int[] numStudents = new int[numPlayers];
		int highestStudent = 0, i = 0, j = 0, colorPos = 0, countHighest = 0;
		Player owner;
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		colorPos = studentColor.get(color);

		//get number of students for each player based on the color
		for(Player player : board.getPlayers()){
			numStudents[i] = player.getPlayerBoard().getDinnerRoom()[colorPos].getNumStudents();
			i++;
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

		if(highestStudent != 0 && countHighest == 0){
			owner = board.getPlayers().get(j);
			board.getProfessors()[colorPos].setOwner(owner);
		}
	}

	public void professorCheck(Character character, Student color) {
		if(character.getEffectId() != 2) professorCheck(color);
		else{
			int[] numStudents = new int[numPlayers];
			int highestStudent = 0, l = 0, k = 0, colorPos;
			Player[] highest = new Player[numPlayers];

			ColorIntMap studentColorMap = new ColorIntMap();
			HashMap<Student, Integer> studentColor = studentColorMap.getMap();

			colorPos = studentColor.get(color);

			//get number of students for each player based on the color
			for(Player player : board.getPlayers()){
				numStudents[k] = player.getPlayerBoard().getDinnerRoom()[colorPos].getNumStudents();
				k++;
			}

			//checks who are the player with highest num of students
			for(int i = 0; i < numPlayers; i++){
				if(numStudents[i] >= highestStudent){
					if(highestStudent == numStudents[i] || highestStudent == 0){
						highest[l] = board.getPlayers().get(i);
						l++;
					}
					highestStudent = numStudents[i];
				}
			}

			//if one of the player with the highest num of student has activated the card gets the professor
			for (Player player : highest){
				if(character.getOwner().equals(player)){
					board.getProfessors()[colorPos].setOwner(player);
				}
			}
		}
	}

	public void conquering() {
		setInfluencePlayer();

		if(influencePlayer != board.getMotherNatureIsland().getOwner()){
			board.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void conquering(Character character) {
		if(character.getEffectId() != 6) conquering();
		else{
			setInfluencePlayer(character);
			board.getMotherNatureIsland().setOwner(influencePlayer);
		}
	}

	public void setInfluencePlayer(Character character){
		influence(character);
		//fix
		for(int i = 0; i < numPlayers; i++){
			if(influencePlayer == null) { influencePlayer = board.getPlayers().get(i);}
			else{
				if (influencePlayer.getInfluenceValue() < board.getPlayers().get(i).getInfluenceValue()) {
					influencePlayer = board.getPlayers().get(i);
				}
			}
		}
	}

	private void influence(Character character){
		if(character.getEffectId() == 7){
			influence(board.getMotherNatureIsland());
			character.getOwner().addInfluence();
			character.getOwner().addInfluence(); //additional 2 influence points
		}
		else if(character.getEffectId() != 6) {
			influence(board.getMotherNatureIsland());
		} else{
			for(Player player : board.getPlayers()) player.setInfluenceValue(0); //reset influence

			//number of students for each color in island, es. numStudent[0] = num of yellow students
			int[] numStudents = board.getMotherNatureIsland().getNumStudents();

			//add influence to each player if the player is the owner, based on the number of students
			for(Player player: board.getPlayers()){
				for(int i = 0; i < 5; i++) {
					if(board.getProfessors()[i].getOwner() != null){
						for(int j = 0; j < numStudents[i]; j++) board.getProfessors()[i].getOwner().addInfluence();
					}
				}
			}
		}


	}
	
	public Character[] getCharacters() {
		return characters;
	}

	public ArrayList<AssistantCard> getCardsPlayed() {
		return cardsPlayed;
	}

	public void moveStudent(Student chosenStudent) {
	}
}

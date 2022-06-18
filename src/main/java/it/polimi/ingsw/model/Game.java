package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import it.polimi.ingsw.model.exceptions.InvalidMoveException;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.network.message.GenericMessage;
import it.polimi.ingsw.network.message.GenericType;
import it.polimi.ingsw.network.message.servermsg.UpdateModelMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages the game logic
 */
public class Game extends Observable {

	private int currentPlayer; //represents the position of currentPlayer in players Array

	private int disabledColor = -1;

	private Player influencePlayer; //the player with the highest influence

	private int currentNumPlayers;

	private final ArrayList<Player> activePlayers = new ArrayList<>();

	private final int numTowers;

	private ArrayList<Player> players = new ArrayList<>();

	private int numPlayers;

	private final GameBoard board;

	private int[] cost;

	private int generalSupply;

	private Character[] characters;

	private ArrayList<Integer> activatedCharacters;

	private ArrayList<AssistantCard> cardsPlayed; //each round has a list of Card containing the card played in the round

	private Random r = new Random();

	private final boolean expertMode;

	/**
	 * Default constructor
	 * @param numPlayers        max number of players the user chose
	 * @param expertMode        true if the expert mode is ON
	 */
	public Game(int numPlayers, boolean expertMode){
		this.expertMode = expertMode;
		board = new GameBoard(numPlayers);
		currentNumPlayers = 0;
		if(numPlayers == 2) numTowers = 8;
		else numTowers = 6;

		this.numPlayers = numPlayers;
		cardsPlayed = new ArrayList<>();
		activatedCharacters = new ArrayList<>();

		if(expertMode){
			board.prepareGame();
			setupExpertMode();
			for(Player player : players) player.addCoin();
		}
		else{
			board.prepareGame();
		}
	}

	/**
	 * Setup for expert mode
	 * Creates the instances of the 3 character cards randomly chose
	 */
	private void setupExpertMode() {
		int id;
		characters = new Character[3];

		int[] availableEffects = {1, 2, 3, 4, 5, 6, 8, 11};
		int[] cost = {0, 1, 2, 3, 1, 2, 3, 0, 2, 0, 0, 2};

		generalSupply = 20 - numPlayers;

		for(int i = 0; i < 3; i++){
			id = chooseEffect(availableEffects);
			characters[i] = new Character(this, id, cost[id]);
		}
	}


	/**
	 * Returns an effect id, used to randomly generate 3 character that will be used in the game
	 *
	 * @param availableEffect		array of available effect that can be generated
	 * @return an effect id, used to generate a character for expert mode
	 */
	private int chooseEffect(int[] availableEffect) {
		int effect;
		int random;

		do {
			random = r.nextInt(8);
			effect = availableEffect[random];

			if(effect != 0){
				availableEffect[random] = 0;
				return effect;
			}
		} while (effect == 0 );

		return 0;
	}

	/**
	 * Returns the current player index in the players arraylist
	 *
	 * @return the current player index in the players arraylist
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}


	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Returns the game board
	 *
	 * @return the game board
	 */
	public GameBoard getBoard() {
		return board;
	}


	/**
	 * Sets influencePlayer as the player with the highest influence, if it
	 * doesn't exist it is set to null
	 */
	private void setInfluencePlayer(Island island){
		influence(island);

		influencePlayer = players
				.stream()
				.max(Comparator.comparing(Player::getInfluenceValue))
				.orElse(null);
	}


	/**
	 * Calculates the influence of a chosen island
	 *
	 * @param island		the island where you want to calculate influence
	 */
	public void influence (Island island) {
		for (Player player : players) player.setInfluenceValue(0); //reset influence

		if(!activatedCharacters.isEmpty() && activatedCharacters.contains(8)){
				Player owner = getCharacterOwner(8);
				owner.addInfluence();
				owner.addInfluence();
		}
		if (!island.isNoEntryTile()) {

			//number of students for each color in island, es. numStudent[0] = num of yellow students
			int[] numStudents = island.getNumStudents();

			//add influence to each player if the player is the owner, based on the number of students
			for (int i = 0; i < 5; i++) {
				if(i != disabledColor){
					for (int j = 0; j < numStudents[i]; j++) {
						if (board.getProfessors()[i].getOwner() != null) board.getProfessors()[i].getOwner().addInfluence();
					}
				}
			}

			//add influence if the player has a tower
			if(!activatedCharacters.isEmpty() && !activatedCharacters.contains(6) && board.getMotherNatureIsland().getOwner() != null){
					board.getMotherNatureIsland().getOwner().addInfluence();
			}
		}
	}


	/**
	 * Unify 2 islands
	 *
	 * @param island1		first island to unify
	 * @param island2		second island to unify
	 */
	public void mergeIslands(Island island1, Island island2) {
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


	/**
	 * Checks if the current island (the one with mother nature on it) can be merged
	 * @return
	 */
	public void mergeCheck(){
		Island island1, island2, island3; //indexes of the islands to compare
		int motherNature, previous, next;

		motherNature = board.getMotherNature();

		next = board.getIslands().getPosition(board.getIslands().getNext(board.getMotherNatureIsland()));

		previous = board.getIslands().getPosition(board.getIslands().getPrevious(board.getMotherNatureIsland()));

		island1 = board.getIslands().get(previous);//previous island
		island2 = board.getMotherNatureIsland();//current island
		island3 = board.getIslands().get(next);//next island

		if(island2.getOwner().equals(island1.getOwner())) {
			mergeIslands(island2, island1);
			board.setMotherNature(motherNature - 1);
			board.getIslands().remove(previous);
			notifyObserver(new UpdateModelMessage(previous, motherNature));
			notifyObserver(new GenericMessage("Island [" + previous + "] and [" + motherNature + "] have been merged", GenericType.MERGE));
		}
		if(island2.getOwner().equals(island3.getOwner())) {
			mergeIslands(island2, island3);
			board.getIslands().remove(next);

			notifyObserver(new UpdateModelMessage(next, motherNature));
			notifyObserver(new GenericMessage("Island [" + next + "] and [" + motherNature + "] have been merged", GenericType.MERGE));
		}
	}

	/**
	 * Returns the current number of players in the game
	 *
	 * @return the current number of players in the game
	 */
	public int getNumPlayers(){
		return numPlayers;
	}

	/**
	 * Calculates the number of students of the color for each player and sets the professor
	 * owner if it is not null
	 * Checks also if the character card with effect 2 is activated
	 *
	 * @param color		the professor color you want to check
	 */

	public boolean professorCheck(Student color){
		int colorPos = createColorIntMap(color);
		String actualOwner = null;

		if(board.getProfessors()[colorPos].getOwner() != null){
			actualOwner = board.getProfessors()[colorPos].getOwner().getUsername();
		}

		int numStud = players
				.stream()
				.mapToInt(p -> p.getNumStudents(color))
				.max()
				.orElse(0);

		List<Player> playersCheck = players
						.stream()
						.filter(p -> p.getNumStudents(color) == numStud)
						.collect(Collectors.toList());

		if(activatedCharacters != null && !activatedCharacters.isEmpty()){
			if(activatedCharacters.contains(2)){
				Character character = getCharacter(2);

				Player owner = character.getOwner();

				if(playersCheck.contains(owner)) board.getProfessors()[colorPos].setOwner(owner);
			}
		}
		else if(playersCheck.size() == 1) board.getProfessors()[colorPos].setOwner(playersCheck.get(0));

		String newOwner = board.getProfessors()[colorPos].getOwner().getUsername();

		if(actualOwner != null && !actualOwner.equals(newOwner)){
			return true;
		}
		else if(actualOwner == null && newOwner != null){
			return true;
		}
		else return false;
	}

	/**
	 * returns the integer associated to the color by using the maps
	 * @param color			the color associated to the position you want to know
	 * @return				the integer associated to the color passed by using the maps
	 */
	public int createColorIntMap(Student color) {
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		return studentColor.get(color);
	}

	public boolean checkAfterMotherNature(){
		boolean isValid = false;

		if(board.getMotherNatureIsland().getOwner() != null){
			Player actualOwner = board.getMotherNatureIsland().getOwner();
		}

		setInfluencePlayer(board.getMotherNatureIsland());

		for(Player player : players){
			if(player.getInfluenceValue() != 0) isValid = true;
		}
		if(setIslandOwner(board.getMotherNatureIsland()) && isValid == true) return true;

		return false;
	}
	/**
	 * After setting the influence player if it is not the actual island owner he conquers it
	 */
	public boolean setIslandOwner(Island island) {
		setInfluencePlayer(island);

		if(influencePlayer != board.getMotherNatureIsland().getOwner()){
			board.getMotherNatureIsland().setOwner(influencePlayer);
			influencePlayer.removeTower();

			return true;
		}
		return false;
	}


	/**
	 * Returns the player that is the owner of the character with the id passed
	 * @param id	id of the effect of the character you want to know the owner
	 * @return		the player that is the owner of the character with the id passed
	 */
	public Player getCharacterOwner(int id) {
		return Arrays.asList(characters)
				.stream()
				.filter(c -> c.getEffectId() == id)
				.collect(Collectors.toList())
				.get(0)
				.getOwner();
	}

	/**
	 * Returns the character with the id passed
	 * @param id	id of the effect of character you want
	 * @return		the character with the id passed
	 */
	public Character getCharacter(int id) {
		return Arrays.asList(characters)
				.stream()
				.filter(c -> c.getEffectId() == id)
				.collect(Collectors.toList())
				.get(0);
	}

	/**
	 * Returns the character of the game
	 * @return	the character of the game
	 */
	public Character[] getCharacters() {
		return characters;
	}


	/**
	 * Return the cards played in this round
	 * @return		 the cards played in this round
	 */
	public List<AssistantCard> getCardsPlayed() {
		return cardsPlayed;
	}

	/**
	 * Returns the currently activated characters
	 * @return		the currently activated characters
	 */
	public List<Integer> getActivatedCharacters() {
		return activatedCharacters;
	}

	public void removeCoins(int numCoins){
		this.generalSupply -= numCoins;
	}

	public void addCoins(int numCoins){
		this.generalSupply += numCoins;
	}

	public int getGeneralSupply() {
		return generalSupply;
	}

	public Player getPlayerByUsername(String username) {
		Player player = players
				.stream()
				.filter(p -> p.getUsername().equals(username))
				.collect(Collectors.toList())
				.get(0);

		return player;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(String username) {
		Player player = new Player(numTowers, username);
		players.add(player);

		if(expertMode){
			player.setNumCoins(3);
		}
	}

	public void setupPhase(){
		for (Player player : players) {
			if (numPlayers == 2) {
				try {
					player.getPlayerBoard().fillEntrance(board.extractStudents(7));
				} catch (EmptyBagException e) {
					e.printStackTrace();
				}
			}
			if (numPlayers == 3) {
				try {
					player.getPlayerBoard().fillEntrance(board.extractStudents(9));
				} catch (EmptyBagException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<>();

		for (Player player : players) {
			usernames.add(player.getUsername());
		}

		return usernames;
	}

	public void setDisabledColor(Student color) {
		disabledColor = createColorIntMap(color);
	}

	public boolean isExpertMode() {
		return expertMode;
	}

	public void startGame(){
		int numStudents;

		if(players.size() == 2) numStudents = 7;
		else numStudents = 9;

		for(Player player : players){
			try {
				player.getPlayerBoard().fillEntrance(board.extractStudents(numStudents));
			} catch (EmptyBagException e) {
				e.printStackTrace();
			}
		}
	}

	public void putStudentInBag(Student student){
		for(Player player : players){
			int num = player.getPlayerBoard().getDinnerRoom()[4].getNumStudents();

			if(num > 3) num = 3;

			do{
				try {
					player.getPlayerBoard().getDinnerRoom()[4].removeStudent(student);
				} catch (InvalidMoveException e) {
					e.printStackTrace();
				}

				board.getBag().add(student);
				num --;
			}while (num > 0);

		}

	}

	public void removePlayer(String user) {
		players.remove(getPlayerByUsername(user));
	}
}

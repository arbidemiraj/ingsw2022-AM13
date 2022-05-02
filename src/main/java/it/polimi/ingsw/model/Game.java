package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages the game logic
 */
public class Game {

	private int currentPlayer; //represents the position of currentPlayer in players Array

	private Player influencePlayer; //the player with the highest influence

	private final GameBoard board;

	private int[] cost;

	private int generalSupply;

	private Character[] characters;

	private ArrayList<Integer> activatedCharacters;

	private ArrayList<AssistantCard> cardsPlayed; //each round has a list of Card containing the card played in the round

	private final int numPlayers;

	private Random r = new Random();

	/**
	 * Default constructor
	 * @param numPlayers		max number of players the user chose
	 * @param expertMode		true if the expert mode is ON
	 */
	public Game(int numPlayers, boolean expertMode){
		board = new GameBoard(numPlayers);
		this.numPlayers = numPlayers;
		activatedCharacters = new ArrayList<>();
		cardsPlayed = new ArrayList<>();

		if(expertMode){
			board.prepareGame();
			setupExpertMode();
			for(Player player : board.getPlayers()) player.addCoin();
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

		influencePlayer = board.getPlayers()
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
		for (Player player : board.getPlayers()) player.setInfluenceValue(0); //reset influence

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
				for (int j = 0; j < numStudents[i]; j++) {
					if (board.getProfessors()[i].getOwner() != null) board.getProfessors()[i].getOwner().addInfluence();
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
		}
		if(island2.getOwner().equals(island3.getOwner())) {
			mergeIslands(island2, island3);
			board.getIslands().remove(next);
		}
	}

	/**
	 * Returns the current number of players in the game
	 *
	 * @return the current number of players in the game
	 */
	public int getNumPlayers(){
		return board.getPlayers().size();
	}

	/**
	 * Calculates the number of students of the color for each player and sets the professor
	 * owner if it is not null
	 * Checks also if the character card with effect 2 is activated
	 *
	 * @param color		the professor color you want to check
	 */

	public void professorCheck(Student color){
		int colorPos = createColorIntMap(color);

		int numStud = board.getPlayers()
				.stream()
				.mapToInt(p -> p.getNumStudents(color))
				.max()
				.orElse(0);

		List<Player> players = board.getPlayers()
						.stream()
						.filter(p -> p.getNumStudents(color) == numStud)
						.collect(Collectors.toList());

		if(!activatedCharacters.isEmpty()){
			if(activatedCharacters.contains(2)){
				Character character = getCharacter(2);

				Player owner = character.getOwner();

				if(players.contains(owner)) board.getProfessors()[colorPos].setOwner(owner);
			}
		}
		else if(players.size() == 1) board.getProfessors()[colorPos].setOwner(players.get(0));
	}

	/**
	 * returns the integer associated to the color by using the maps
	 * @param color			the color associated to the position you want to know
	 * @return				the integer associated to the color passed by using the maps
	 */
	private int createColorIntMap(Student color) {
		ColorIntMap studentColorMap = new ColorIntMap();
		HashMap<Student, Integer> studentColor = studentColorMap.getMap();

		return studentColor.get(color);
	}

	/**
	 * After setting the influence player if it is not the actual island owner he conquers it
	 */
	public void setIslandOwner(Island island) {
		setInfluencePlayer(island);

		if(influencePlayer != board.getMotherNatureIsland().getOwner()){
			board.getMotherNatureIsland().setOwner(influencePlayer);
		}
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
}

package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;

import java.util.ArrayList;

/**
 * Characters class
 */
public class Character {

	private int cost;

	private final Actionable effect;

	private String desc;

	private final int effectId;

	private boolean isActivated;

	private Player owner;

	private final Game game;

	/**
	 * Default constructor
	 * @param game	model
	 * @param id	the id of the effect
	 * @param cost	the cost of the character card
	 */
	public Character(Game game, int id, int cost) {
		this.game = game;
		isActivated = false;
		effectId = id;
		this.cost = cost;

		switch(effectId){
			case 1:
				ArrayList<Student> students1 = null;
				try {
					students1 = game.getBoard().extractStudents(4);
				} catch (EmptyBagException e) {

				}
				effect = new Effect1(students1);
				break;

			case 3:
				effect = new Effect3();
				break;

			case 5:
				effect = new Effect5();
				break;

			case 7:
				ArrayList<Student> students7 = null;
				try {
					students7 = game.getBoard().extractStudents(6);
				} catch (EmptyBagException e) {
				}
				effect = new Effect7(students7);

				break;

			case 9:
				effect = new Effect9();
				break;

			case 11:
				ArrayList<Student> students11 = null;
				try {
					students11 = game.getBoard().extractStudents(4);
				} catch (EmptyBagException e) {
				}
				effect = new Effect11(students11);
				break;
			case 12:
				effect = new Effect12();
				break;
			default:
				effect = new Effect();
				break;
		}
	}

	public Actionable getEffect() {
		return effect;
	}

	public int getEffectId() {
		return effectId;
	}

	public Player getOwner() {
		return owner;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getCost() {
		return cost;
	}

	public String getDesc(){
		return desc;
	}

	/**
	 * method called when a card that needs a students as input is activated
	 * @param chosenStudent		the student given in input
	 * @throws EmptyBagException	if the bag is empty after moving throws the exception to end the game
	 */
	public void applyEffect(Student chosenStudent) throws EmptyBagException {
		isActivated = true;
		effect.apply(game, chosenStudent);
	}

	/**
	 * method called when the card is activated on a chosenIsland
	 * @param chosenIsland	the chosen Island
	 * @throws EmptyBagException	if the bag is empty after activating throws the exception to end the game
	 */
	public void applyEffect(Island chosenIsland) throws EmptyBagException {
		isActivated = true;
		effect.apply(game, chosenIsland);
	}

	/**
	 * Generic character card activation
	 */
	public void applyEffect(){
		isActivated = true;
		effect.apply(game);
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}

package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Character {

	private int cost;

	private Actionable effect;

	private String desc;

	private final int effectId;

	private boolean isActivated;

	private Player owner;

	private Game game;

	public Character(Game game, int id, int cost) {
		this.game = game;
		isActivated = false;
		effectId = id;
		this.cost = cost;

		switch(effectId){
			case 1:
				ArrayList<Student> students1 = game.getBoard().extractStudents(4);
				effect = new Effect1(students1);
				break;

			case 3:
				effect = new Effect3();
				break;

			case 5:
				effect = new Effect5();
				break;

			case 7:
				ArrayList<Student> students7 = game.getBoard().extractStudents(6);
				effect = new Effect7(students7);
				break;

			case 9:

			case 11:
				ArrayList<Student> students11 = game.getBoard().extractStudents(4);
				effect = new Effect11(students11);
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
	//method for the cards that needs a chosenStudent to apply the effect
	public void applyEffect(Student chosenStudent){
		isActivated = true;
		effect.apply(game, chosenStudent);
	}

	//method for the cards that needs a chosen Island to apply the effect
	public void applyEffect(Island chosenIsland){
		isActivated = true;
		effect.apply(game, chosenIsland);
	}

	public void applyEffect(){
		isActivated = true;
		effect.apply(game);
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}

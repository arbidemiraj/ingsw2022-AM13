package it.polimi.ingsw;

import java.util.ArrayList;

public class Character {

	private int cost;

	private Effect effect;

	private final int effectId;

	private boolean isActivated;

	private Player owner;

	private Game game;

	public Character(Game game, int id) {
		this.game = game;

		effectId = id;

		if (id == 1) {
			ArrayList<Student> students = game.getTable().extractStudents(4);
			effect = new Effect1(id, students);
			cost = 1;
		}

		if (id == 2){
			effect = new Effect2(id);
			cost = 2;
		}

		if (id == 3){
			effect = new Effect3(id);
			cost = 3;
		}

		if (id == 4){
			effect = new Effect4(id);
			cost = 1;
		}

		if (id == 5){
			effect = new Effect5(id);
			cost = 2;
		}

		if (id == 6){
			effect = new Effect6(id);
			cost = 3;
		}

		if (id == 8){
			effect = new Effect8(id);
			cost = 2;
		}

		if (id == 11) {
			ArrayList<Student> students = game.getTable().extractStudents(4);
			effect = new Effect11(id, students);
			cost = 2;
		}
	}

	public Effect getEffect() {
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

	public void applyEffect(Student chosenStudent){
		isActivated = true;

		effect.apply(game, chosenStudent);


	}

	public int getCost() {
		return cost;
	}

	public void applyEffect(Island chosenIsland){
		isActivated = true;

		effect.apply(game, chosenIsland);

	}

	public void applyEffect(){
		isActivated = true;
		effect.apply(game);
	}
}

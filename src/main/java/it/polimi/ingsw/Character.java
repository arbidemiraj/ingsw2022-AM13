package it.polimi.ingsw;

import java.util.ArrayList;

public class Character {

	private int cost;

	private Effect effect;

	private int effectId;

	private boolean isActivated;

	private Player owner;

	public Character(Game game, int id) {
		effectId = id;
		if(id == 1){
			game.getTable().extractStudents(4);
			ArrayList<Student> students = game.getTable().getExtractedStudents();

			effect = new Effect1(id, students);
		}
		if(id == 2) effect = new Effect2(id);
		if(id == 3) effect = new Effect3(id);
		if(id == 4) effect = new Effect4(id);
		if(id == 5) effect = new Effect5(id);
		if(id == 6) effect = new Effect6(id);
		if(id == 7) effect = new Effect7(id);
		if(id == 11){
			game.getTable().extractStudents(4);
			ArrayList<Student> students = game.getTable().getExtractedStudents();
			effect = new Effect11(id, students);
		}
	}

	public Effect getEffect() {
		return effect;
	}

	public int getEffectId(){
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

	public void activateEffect() {
		isActivated = true;
	}
}

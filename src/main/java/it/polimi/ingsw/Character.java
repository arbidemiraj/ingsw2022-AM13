package it.polimi.ingsw;

public class Character {

	private int cost;

	private Effect effect;

	private boolean isActivated;

	public Character(Game game, int id) {
		if(id == 1) effect = new Effect1(id);
		if(id == 2) effect = new Effect2(id);
		if(id == 3) effect = new Effect3(id);
		if(id == 4) effect = new Effect4(id);
		if(id == 5) effect = new Effect5(id);
		if(id == 6) effect = new Effect6(id);
		if(id == 7) effect = new Effect7(id);
		if(id == 11) effect = new Effect11(id);
	}

	public Effect getEffect() {
		return null;
	}

}

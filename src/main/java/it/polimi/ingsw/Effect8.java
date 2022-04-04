package it.polimi.ingsw;

public class Effect8 implements Effect{

	private int id;

	public Effect8(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void apply(Game game) {

	}

	public void apply(Game game, Character character) {
		character.activateEffect();
	}
}

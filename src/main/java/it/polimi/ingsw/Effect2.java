package it.polimi.ingsw;

public class Effect2 implements Effect{

	private int id;

	public void apply(Game game, Character character) {
		character.activateEffect();
	}

	public Effect2(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void apply(Game game) {
	}
}

package it.polimi.ingsw;

public class Effect6 implements Effect{

	private int id;

	public Effect6(int id) {
		this.id = id;
	}

	public int getId() {
		return 0;
	}

	@Override
	public void apply(Game game) {
	}

	public void apply(Game game, Character character) {
		character.activateEffect();
	}
}

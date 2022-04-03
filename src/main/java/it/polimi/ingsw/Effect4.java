package it.polimi.ingsw;

public class Effect4 implements Effect{

	private int id;

	public Effect4(int id) {
		this.id = id;
	}

	public int getId() {
		return 0;
	}

	@Override
	public void apply(Game game) {
		game.addMotherNatureMoves();
	}
}

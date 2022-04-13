package it.polimi.ingsw.effects;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Island;
import it.polimi.ingsw.Student;

public class Effect3 implements Effect{

	private int id;

	public void apply(Game game, Island chosenIsland) {
		game.influence(chosenIsland);
	}

	public Effect3(int id) {
		this.id = id;
	}

	public int getId() {
		return 0;
	}

	@Override
	public void apply(Game game) {
	}

	@Override
	public void apply(Game game, Student chosenStudent) {

	}
}

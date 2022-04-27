package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

public class Effect3 implements Actionable {

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

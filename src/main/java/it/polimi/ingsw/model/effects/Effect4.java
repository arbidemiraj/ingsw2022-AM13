package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

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

	@Override
	public void apply(Game game, Student chosenStudent) {

	}

	@Override
	public void apply(Game game, Island chosenIsland) {

	}
}

package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

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

	@Override
	public void apply(Game game, Student chosenStudent) {

	}

	@Override
	public void apply(Game game, Island chosenIsland) {

	}
}

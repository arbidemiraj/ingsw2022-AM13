package it.polimi.ingsw.effects;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Island;
import it.polimi.ingsw.Student;

public class Effect5 implements Effect{

	private int id;
	private int noEntryTiles;
	public Effect5(int id) {
		this.id = id;
		noEntryTiles = 4;
	}

	public int getId() {
		return 0;
	}


	public void apply(Game game, Island chosenIsland) {
		chosenIsland.setNoEntryTile();
		noEntryTiles--;
	}


	@Override
	public void apply(Game game) {

	}

	@Override
	public void apply(Game game, Student chosenStudent) {

	}
}

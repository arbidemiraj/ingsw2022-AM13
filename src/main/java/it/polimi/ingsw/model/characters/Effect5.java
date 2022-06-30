package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

/**
 * Class for the fifth character effect
 */
public class Effect5 implements Actionable {

	private int numEntryTiles;

	public Effect5() {
		numEntryTiles = 4;
	}

	public void apply(Game game , Island chosenIsland) {
		chosenIsland.setNoEntryTile();

		numEntryTiles--;
	}

	public int getNumEntryTiles() {
		return numEntryTiles;
	}

	public void addEntryTile(){
		this.numEntryTiles++;
	}
	@Override
	public void apply(Game game ) {
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
	}
}

package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

/**
 * Class for generic effects that only changes game rules
 */
public class Effect implements Actionable {
	@Override
	public void apply(Game game ) {
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
	}

	@Override
	public void apply(Game game , Island chosenIsland) {
	}
}

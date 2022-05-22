package it.polimi.ingsw.model.characters;

import com.sun.nio.sctp.AbstractNotificationHandler;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

public class Effect3 implements Actionable {
	private String desc;

	public void apply(Game game , Island chosenIsland) {
		game.setIslandOwner(chosenIsland);
	}

	@Override
	public void apply(Game game ) {
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
	}
}

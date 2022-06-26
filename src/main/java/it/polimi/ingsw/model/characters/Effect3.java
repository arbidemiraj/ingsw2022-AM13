package it.polimi.ingsw.model.characters;

import com.sun.nio.sctp.AbstractNotificationHandler;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;

public class Effect3 implements Actionable {
	private String desc;

	public void apply(Game game , Island chosenIsland) {
		boolean isValid = false;

		for(Player player : game.getPlayers()){
			if(player.getInfluenceValue() != 0) isValid = true;
		}

		if(game.setIslandOwner(chosenIsland) && isValid == true) game.updateConquer(game.getBoard().getIslands().getPosition(chosenIsland));
	}

	@Override
	public void apply(Game game ) {
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
	}
}

package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;

public interface Actionable {

	void apply(Game game );
	void apply(Game game , Student chosenStudent) throws EmptyBagException;
	void apply(Game game , Island chosenIsland) throws EmptyBagException;
}

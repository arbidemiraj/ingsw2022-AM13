package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

public interface Actionable {

	void apply(Game game );
	void apply(Game game , Student chosenStudent);
	void apply(Game game , Island chosenIsland);
}

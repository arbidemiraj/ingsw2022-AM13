package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

public interface Effect {

	public abstract void apply(Game game);
	public abstract void apply(Game game, Student chosenStudent);
	public abstract void apply(Game game, Island chosenIsland);


}

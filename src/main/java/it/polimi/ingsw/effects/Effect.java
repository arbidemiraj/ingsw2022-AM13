package it.polimi.ingsw.effects;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Island;
import it.polimi.ingsw.Student;

public interface Effect {

	public abstract void apply(Game game);
	public abstract void apply(Game game, Student chosenStudent);
	public abstract void apply(Game game, Island chosenIsland);


}

package it.polimi.ingsw;

public interface Effect {

	public abstract void apply(Game game);
	public abstract void apply(Game game, Student chosenStudent);
	public abstract void apply(Game game, Island chosenIsland);


}

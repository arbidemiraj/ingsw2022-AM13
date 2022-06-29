package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;

/**
 * interface used for the cards effect, as a similar Strategy Pattern
 */
public interface Actionable {
	/**
	 * Method called for generic character cards
	 * @param game	model
	 */
	void apply(Game game );

	/**
	 * Method called for cards which effect is applied on students
	 * @param game	model
	 * @param chosenStudent	the student chosen by the user
	 * @throws EmptyBagException
	 */
	void apply(Game game , Student chosenStudent) throws EmptyBagException;

	/**
	 * Method called for cards which effect is applied on an Island
	 * @param game	model
	 * @param chosenIsland	the island chosen by the user
	 * @throws EmptyBagException
	 */
	void apply(Game game , Island chosenIsland) throws EmptyBagException;
}

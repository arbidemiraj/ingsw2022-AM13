package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This classes manages the assistant cards
 */
public class AssistantCard implements Serializable {


	@Serial
	private static final long serialVersionUID = 9031182714517267067L;

	private int value;

	private int maxMotherNatureMoves;

	/**
	 * Default constructor of the assistant cards
	 * @param value		Value of the assistant card
	 * @param maxMotherNatureMoves		Max moves of mother nature you can do when playing this card
	 */
	public AssistantCard(int value, int maxMotherNatureMoves){
		this.value = value;
		this.maxMotherNatureMoves = maxMotherNatureMoves;
	}


	/**
	 * Returns the value of this card
	 * @return		the value of this card
	 */
	public int getValue() {
		return value;
	}


	/**
	 * Returns the moves the player can make when playing this card
	 * @return		the moves the player can make when playing this card
	 */
	public int getMaxMotherNatureMoves() {
		return maxMotherNatureMoves;
	}

}

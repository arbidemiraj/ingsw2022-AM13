package it.polimi.ingsw;

public class AssistantCard {

	private int value;

	private int motherNatureMoves;

	public AssistantCard(int value, int motherNatureMoves){
		this.value = value;
		this.motherNatureMoves = motherNatureMoves;
	}
	public int getValue() {
		return value;
	}

	public int getMotherNatureMoves() {
		return motherNatureMoves;
	}

}

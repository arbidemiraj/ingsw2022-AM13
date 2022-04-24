package it.polimi.ingsw.model;

public class AssistantCard {

	private int value;

	private int maxMotherNatureMoves;

	public AssistantCard(int value, int maxMotherNatureMoves){
		this.value = value;
		this.maxMotherNatureMoves = maxMotherNatureMoves;
	}
	public int getValue() {
		return value;
	}

	public int getMaxMotherNatureMoves() {
		return maxMotherNatureMoves;
	}

}

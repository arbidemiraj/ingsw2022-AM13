package it.polimi.ingsw;

public class Professor {

	private Student color;

	private Player owner;

	public Professor(Student color){
		this.color = color;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public Student getColor() {
		return color;
	}
}

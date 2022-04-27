package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Effect11 implements Actionable {

	private int id;
	private ArrayList<Student> students;

	public Effect11(int id, ArrayList<Student> students) {
		this.id = id;
		this.students = students;
	}

	public int getId() {
		return 0;
	}

	@Override
	public void apply(Game game, Student chosenStudent) {

	}

	@Override
	public void apply(Game game, Island chosenIsland) {

	}

	@Override
	public void apply(Game game) {

	}
}

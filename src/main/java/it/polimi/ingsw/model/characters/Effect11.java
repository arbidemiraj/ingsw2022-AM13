package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;

import java.util.ArrayList;

public class Effect11 implements Actionable {
	private ArrayList<Student> students;

	public Effect11(ArrayList<Student> students) {
		this.students = students;
	}

	@Override
	public void apply(Game game , Student chosenStudent) {
		Player player = game.getBoard().getPlayers().get(game.getCurrentPlayer());
		player.getPlayerBoard().addStudent(chosenStudent);
		students.remove(chosenStudent);
		students.add(game.getBoard().extractStudents(1).get(0));
	}

	@Override
	public void apply(Game game , Island chosenIsland) {
	}

	@Override
	public void apply(Game game ) {
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
}

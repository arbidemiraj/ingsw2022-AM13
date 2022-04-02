package it.polimi.ingsw;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Island {

	private ArrayList<Student> students;

	private Player owner;

	private IslandState islandState;

	public Island() {
		students = new ArrayList<>();
		owner = null;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void changeState() {
		islandState = new UnifiedIsland();
	}


	public void OwnerCheck() {
		//modifico la funzione per verificare le influenze
		int inf1,inf2;
		//ciclo for che calcola le influenze di tutti i player
		inf1=influence(studens,player1);
		inf2=influence(studens,player2);
		//find who has more influence to own the island
		if(inf1>inf2)
			setOwner(player1);
		else if (inf1<inf2)
			setOwner(player2);
	}

	public void addStudent(Student student) {
		students.add(students.size()-1, student);
	}
}

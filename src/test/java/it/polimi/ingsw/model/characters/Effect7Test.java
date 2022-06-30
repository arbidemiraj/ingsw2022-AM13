package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect7Test {
    
    @Test
    void removeStudent() {
        Game game = new Game(2, true);
        Effect7 effect7 = null;
        
        try {
            effect7 = new Effect7(game.getBoard().extractStudents(6));
            effect7.removeStudent(effect7.getStudents().get(0));
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
        
        assertEquals(effect7.getStudents().size(), 5);
    }

    @Test
    void addStudent() {
        Game game = new Game(2, true);
        Effect7 effect7 = null;

        try {
            effect7 = new Effect7(game.getBoard().extractStudents(6));
            effect7.addStudent(Student.BLUE);
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }

        assertEquals(7, effect7.getStudents().size());
        assertEquals(Student.BLUE, effect7.getStudents().get(6));
    }
}
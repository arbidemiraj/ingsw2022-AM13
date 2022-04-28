package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect1Test {

    @Test
    void apply() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();

        Effect1 effect1 = new Effect1(game.getBoard().extractStudents(4));
        Student chosenStudent = effect1.getStudents().get(0);
        effect1.apply(game, chosenStudent);
        effect1.apply(game, game.getBoard().getMotherNatureIsland());
        assertTrue(game.getBoard().getMotherNatureIsland().getStudents().contains(chosenStudent));
        assertTrue(effect1.getStudents().size() == 4);
    }
}
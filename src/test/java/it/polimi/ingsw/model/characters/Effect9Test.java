package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect9Test {

    @Test
    void apply() {
        Game game = new Game(2, true);
        Effect9 effect9 = new Effect9();

        effect9.apply(game, Student.RED);

        assertEquals(4, game.getDisabledColor());
    }
}